package com.beinganujchaudhary.aetherread.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.security.MessageDigest
import java.util.UUID

object PdfUtils {
    
    private const val TAG = "PdfUtils"

    data class ImportResult(
        val success: Boolean,
        val documentId: String? = null,
        val title: String? = null,
        val pageCount: Int = 0,
        val fileSizeBytes: Long = 0,
        val fileHash: String? = null,
        val localFilePath: String? = null,
        val coverPagePath: String? = null,
        val errorMessage: String? = null
    )

    /**
     * Imports a PDF from a given Uri.
     * Copies the file to internal storage, generates a SHA-256 hash,
     * extracts basic metadata, and generates a cover thumbnail.
     */
    suspend fun importPdf(context: Context, uri: Uri): ImportResult = withContext(Dispatchers.IO) {
        try {
            val contentResolver = context.contentResolver
            
            // 1. Get file name and size
            var displayName = "Unknown Document"
            var sizeBytes = 0L
            contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex != -1) {
                        displayName = cursor.getString(nameIndex)
                    }
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (sizeIndex != -1) {
                        sizeBytes = cursor.getLong(sizeIndex)
                    }
                }
            }

            // Remove .pdf extension from title if present
            if (displayName.lowercase().endsWith(".pdf")) {
                displayName = displayName.substring(0, displayName.length - 4)
            }

            // 2. Generate Hash & Copy to Internal Storage
            val documentId = UUID.randomUUID().toString()
            val fileName = "$documentId.pdf"
            val destFile = File(context.filesDir, fileName)
            
            var hashString = ""
            
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(destFile).use { outputStream ->
                    hashString = copyAndHash(inputStream, outputStream)
                }
            } ?: return@withContext ImportResult(false, errorMessage = "Could not open input stream")

            if (sizeBytes == 0L) {
                sizeBytes = destFile.length()
            }

            // 3. Open with PdfRenderer to get page count and generate cover
            var pageCount = 0
            var coverPath: String? = null

            try {
                val fileDescriptor = android.os.ParcelFileDescriptor.open(destFile, android.os.ParcelFileDescriptor.MODE_READ_ONLY)
                val pdfRenderer = PdfRenderer(fileDescriptor)
                pageCount = pdfRenderer.pageCount

                if (pageCount > 0) {
                    val page = pdfRenderer.openPage(0)
                    // Render cover page
                    // Calculate dimensions, max width 400px
                    val width = 400
                    val height = (width.toFloat() / page.width * page.height).toInt()
                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    // Fill white background
                    bitmap.eraseColor(android.graphics.Color.WHITE)
                    
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()

                    // Save bitmap to file
                    val coverFile = File(context.filesDir, "${documentId}_cover.png")
                    FileOutputStream(coverFile).use { out ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    }
                    coverPath = coverFile.absolutePath
                    bitmap.recycle()
                }

                pdfRenderer.close()
                fileDescriptor.close()
            } catch (e: Exception) {
                Log.e(TAG, "Error generating PDF cover", e)
                // Continue even if cover fails
            }

            ImportResult(
                success = true,
                documentId = documentId,
                title = displayName,
                pageCount = pageCount,
                fileSizeBytes = sizeBytes,
                fileHash = hashString,
                localFilePath = destFile.absolutePath,
                coverPagePath = coverPath
            )

        } catch (e: Exception) {
            Log.e(TAG, "Failed to import PDF", e)
            ImportResult(success = false, errorMessage = e.message)
        }
    }

    private fun copyAndHash(input: InputStream, output: FileOutputStream): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val buffer = ByteArray(8192)
        var bytesRead: Int
        while (input.read(buffer).also { bytesRead = it } != -1) {
            output.write(buffer, 0, bytesRead)
            digest.update(buffer, 0, bytesRead)
        }
        val hashBytes = digest.digest()
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
