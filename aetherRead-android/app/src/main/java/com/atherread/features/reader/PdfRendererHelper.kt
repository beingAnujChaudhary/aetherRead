package com.atherread.features.reader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

class PdfRendererHelper(private val context: Context, private val uriStr: String) {

    private var fileDescriptor: ParcelFileDescriptor? = null
    private var pdfRenderer: PdfRenderer? = null

    suspend fun openPdf(): Int? = withContext(Dispatchers.IO) {
        try {
            val uri = Uri.parse(uriStr)
            fileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            if (fileDescriptor != null) {
                pdfRenderer = PdfRenderer(fileDescriptor!!)
                pdfRenderer?.pageCount
            } else {
                null
            }
        } catch (e: Exception) {
            Timber.e(e, "Error opening PDF")
            null
        }
    }

    suspend fun renderPage(pageIndex: Int, width: Int): Bitmap? = withContext(Dispatchers.IO) {
        val renderer = pdfRenderer ?: return@withContext null
        if (pageIndex < 0 || pageIndex >= renderer.pageCount) return@withContext null

        var page: PdfRenderer.Page? = null
        try {
            page = renderer.openPage(pageIndex)
            val aspectRatio = page.width.toFloat() / page.height.toFloat()
            val height = (width / aspectRatio).toInt()
            
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            // PDF backgrounds are transparent by default, fill with white
            bitmap.eraseColor(android.graphics.Color.WHITE)
            
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmap
        } catch (e: Exception) {
            Timber.e(e, "Error rendering page $pageIndex")
            null
        } finally {
            page?.close()
        }
    }

    fun close() {
        pdfRenderer?.close()
        fileDescriptor?.close()
    }
}
