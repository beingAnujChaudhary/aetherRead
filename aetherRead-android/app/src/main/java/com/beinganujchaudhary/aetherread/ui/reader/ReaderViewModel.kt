package com.beinganujchaudhary.aetherread.ui.reader

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beinganujchaudhary.aetherread.data.repository.DocumentRepository
import com.beinganujchaudhary.aetherread.data.repository.ReadingStateRepository
import com.beinganujchaudhary.aetherread.domain.model.ComfortTheme
import com.beinganujchaudhary.aetherread.domain.model.Document
import com.beinganujchaudhary.aetherread.domain.model.ReadingState
import com.beinganujchaudhary.aetherread.ui.reader.Line
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val documentRepository: DocumentRepository,
    private val readingStateRepository: ReadingStateRepository
) : ViewModel() {

    private val documentId: String = checkNotNull(savedStateHandle["documentId"])

    private val _document = MutableStateFlow<Document?>(null)
    val document: StateFlow<Document?> = _document.asStateFlow()

    private val _readingState = MutableStateFlow<ReadingState?>(null)
    val readingState: StateFlow<ReadingState?> = _readingState.asStateFlow()

    private val _pageCount = MutableStateFlow(0)
    val pageCount: StateFlow<Int> = _pageCount.asStateFlow()

    // Annotations state: maps pageIndex to a list of lines
    private val _annotations = MutableStateFlow<Map<Int, List<Line>>>(emptyMap())
    val annotations: StateFlow<Map<Int, List<Line>>> = _annotations.asStateFlow()

    private var pdfRenderer: PdfRenderer? = null
    private var fileDescriptor: ParcelFileDescriptor? = null
    private val rendererMutex = Mutex()

    init {
        loadDocument()
    }

    private fun loadDocument() {
        viewModelScope.launch {
            val doc = documentRepository.getDocument(documentId)
            if (doc != null) {
                _document.value = doc
                initPdfRenderer(doc.localFilePath)
                loadReadingState(doc.id)
            }
        }
    }

    private suspend fun initPdfRenderer(filePath: String) {
        withContext(Dispatchers.IO) {
            try {
                val file = File(filePath)
                if (file.exists()) {
                    fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                    pdfRenderer = PdfRenderer(fileDescriptor!!)
                    _pageCount.value = pdfRenderer?.pageCount ?: 0
                }
            } catch (e: Exception) {
                Log.e("ReaderViewModel", "Failed to init PdfRenderer", e)
            }
        }
    }

    private suspend fun loadReadingState(docId: String) {
        var state = readingStateRepository.getReadingState(docId)
        if (state == null) {
            state = ReadingState(
                documentId = docId,
                updatedAt = System.currentTimeMillis()
            )
        }
        _readingState.value = state
    }

    fun updateTheme(theme: ComfortTheme) {
        val currentState = _readingState.value ?: return
        val newState = currentState.copy(activeTheme = theme, updatedAt = System.currentTimeMillis())
        _readingState.value = newState
        saveReadingState(newState)
    }

    fun updateCurrentPage(pageIndex: Int) {
        val currentState = _readingState.value ?: return
        val pageNumber = pageIndex + 1
        if (currentState.currentPage != pageNumber) {
            val newState = currentState.copy(currentPage = pageNumber, updatedAt = System.currentTimeMillis())
            _readingState.value = newState
            saveReadingState(newState)
        }
    }

    fun addAnnotation(pageIndex: Int, line: Line) {
        val currentAnnotations = _annotations.value.toMutableMap()
        val pageAnnotations = currentAnnotations[pageIndex]?.toMutableList() ?: mutableListOf()
        pageAnnotations.add(line)
        currentAnnotations[pageIndex] = pageAnnotations
        _annotations.value = currentAnnotations
    }

    private fun saveReadingState(state: ReadingState) {
        viewModelScope.launch {
            readingStateRepository.saveReadingState(state)
        }
    }

    suspend fun renderPage(pageIndex: Int, width: Int): Bitmap? {
        return withContext(Dispatchers.IO) {
            rendererMutex.withLock {
                try {
                    val renderer = pdfRenderer ?: return@withLock null
                    if (pageIndex < 0 || pageIndex >= renderer.pageCount) return@withLock null

                    val page = renderer.openPage(pageIndex)
                    val height = (width.toFloat() / page.width * page.height).toInt()
                    
                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    bitmap.eraseColor(android.graphics.Color.WHITE)
                    
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()
                    bitmap
                } catch (e: Exception) {
                    Log.e("ReaderViewModel", "Failed to render page $pageIndex", e)
                    null
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            pdfRenderer?.close()
            fileDescriptor?.close()
        } catch (e: Exception) {
            Log.e("ReaderViewModel", "Error closing PdfRenderer", e)
        }
    }
}
