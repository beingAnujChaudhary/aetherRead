package com.atherread.features.home

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atherread.data.local.room.PdfEntity
import com.atherread.data.repository.PdfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pdfRepository: PdfRepository
) : ViewModel() {

    val recentPdfs: StateFlow<List<PdfEntity>> = pdfRepository.getAllPdfs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onPdfOpened(uri: Uri, context: Context, onNavigateToPdf: (String) -> Unit) {
        viewModelScope.launch {
            val name = getFileName(context, uri) ?: "Unknown PDF"
            val path = uri.toString()
            
            val existing = pdfRepository.getPdfByPath(path)
            if (existing == null) {
                pdfRepository.insertOrUpdatePdf(
                    PdfEntity(
                        path = path,
                        name = name,
                        lastOpened = System.currentTimeMillis(),
                        totalPages = 0,
                        currentPage = 0,
                        isFavorite = false
                    )
                )
            } else {
                pdfRepository.updateLastOpened(path, System.currentTimeMillis())
            }
            
            val encodedPath = java.net.URLEncoder.encode(path, "UTF-8")
            onNavigateToPdf(encodedPath)
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index != -1) {
                        result = cursor.getString(index)
                    }
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path?.let { path ->
                val cut = path.lastIndexOf('/')
                if (cut != -1) path.substring(cut + 1) else path
            }
        }
        return result
    }
}
