package com.atherread.features.reader

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor() : ViewModel() {

    private var pdfRendererHelper: PdfRendererHelper? = null

    private val _pageCount = MutableStateFlow(0)
    val pageCount: StateFlow<Int> = _pageCount.asStateFlow()

    private val _renderedPages = MutableStateFlow<Map<Int, Bitmap>>(emptyMap())
    val renderedPages: StateFlow<Map<Int, Bitmap>> = _renderedPages.asStateFlow()

    fun initPdf(context: Context, uriStr: String) {
        viewModelScope.launch {
            pdfRendererHelper?.close()
            pdfRendererHelper = PdfRendererHelper(context, uriStr)
            val count = pdfRendererHelper?.openPdf() ?: 0
            _pageCount.value = count
        }
    }

    fun loadPage(pageIndex: Int, width: Int) {
        if (_renderedPages.value.containsKey(pageIndex)) return
        
        viewModelScope.launch {
            val bitmap = pdfRendererHelper?.renderPage(pageIndex, width)
            if (bitmap != null) {
                _renderedPages.value = _renderedPages.value.toMutableMap().apply {
                    put(pageIndex, bitmap)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        pdfRendererHelper?.close()
        _renderedPages.value.values.forEach { it.recycle() }
    }
}
