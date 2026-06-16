package com.beinganujchaudhary.aetherread.ui.library

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beinganujchaudhary.aetherread.data.repository.DocumentRepository
import com.beinganujchaudhary.aetherread.domain.model.Document
import com.beinganujchaudhary.aetherread.util.PdfUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

enum class SortOrder {
    RECENT, TITLE, PROGRESS
}

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val documentRepository: DocumentRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _sortOrder = MutableStateFlow(SortOrder.RECENT)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _isImporting = MutableStateFlow(false)
    val isImporting: StateFlow<Boolean> = _isImporting.asStateFlow()

    private val _importError = MutableStateFlow<String?>(null)
    val importError: StateFlow<String?> = _importError.asStateFlow()

    val documents: StateFlow<List<Document>> = combine(
        documentRepository.getAllDocuments(),
        _searchQuery,
        _sortOrder
    ) { docs, query, sort ->
        var filtered = docs
        if (query.isNotBlank()) {
            filtered = filtered.filter { it.title.contains(query, ignoreCase = true) }
        }
        when (sort) {
            SortOrder.RECENT -> filtered.sortedByDescending { it.lastOpenedAt ?: it.uploadedAt }
            SortOrder.TITLE -> filtered.sortedBy { it.title.lowercase() }
            SortOrder.PROGRESS -> filtered.sortedByDescending { it.totalPagesRead.toFloat() / maxOf(1, it.pageCount) }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSortOrderChanged(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
    }

    fun clearImportError() {
        _importError.value = null
    }

    fun importPdf(uri: Uri) {
        viewModelScope.launch {
            _isImporting.value = true
            _importError.value = null

            val result = PdfUtils.importPdf(context, uri)

            if (result.success && result.documentId != null) {
                // Check if duplicate by hash
                val hash = result.fileHash
                if (hash != null) {
                    val existing = documentRepository.findByHash(hash)
                    if (existing != null) {
                        _importError.value = "Document already exists in library."
                        // Delete the newly copied file to save space
                        result.localFilePath?.let { File(it).delete() }
                        result.coverPagePath?.let { File(it).delete() }
                        _isImporting.value = false
                        return@launch
                    }
                }

                val doc = Document(
                    id = result.documentId,
                    title = result.title ?: "Unknown",
                    pageCount = result.pageCount,
                    fileSizeBytes = result.fileSizeBytes,
                    fileHash = result.fileHash ?: "",
                    localFilePath = result.localFilePath ?: "",
                    coverPageUri = result.coverPagePath,
                    uploadedAt = System.currentTimeMillis()
                )
                documentRepository.insertDocument(doc)
            } else {
                _importError.value = result.errorMessage ?: "Failed to import PDF"
            }

            _isImporting.value = false
        }
    }

    fun deleteDocument(document: Document) {
        viewModelScope.launch {
            // 1. Delete files from storage
            try {
                File(document.localFilePath).delete()
                document.coverPageUri?.let { File(it).delete() }
            } catch (e: Exception) {
                Log.e("LibraryViewModel", "Error deleting files", e)
            }
            
            // 2. Delete from DB (cascade should handle annotations and reading state)
            documentRepository.deleteDocument(document.id)
        }
    }
}
