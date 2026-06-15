package com.beinganujchaudhary.aetherread.domain.model

/**
 * Core domain model for a PDF document.
 * Mirrors the web app's Document interface in lib/db.ts.
 */
data class Document(
    val id: String,
    val title: String,
    val author: String? = null,
    val pageCount: Int,
    val fileSizeBytes: Long,
    val fileHash: String,           // SHA-256 for duplicate detection
    val localFilePath: String,      // Absolute path on device storage
    val coverPageUri: String? = null,
    val uploadedAt: Long,           // epoch millis
    val lastOpenedAt: Long? = null,
    val totalPagesRead: Int = 0,
)

/**
 * Annotation attached to a specific page of a document.
 */
data class Annotation(
    val id: String,
    val documentId: String,
    val pageNumber: Int,
    val category: AnnotationCategory,
    val note: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean = false,
)

enum class AnnotationCategory {
    IMPORTANT, DEFINITION, QUESTION, REVISION, QUOTE;
}

/**
 * Persisted reading state — restored on next open.
 */
data class ReadingState(
    val documentId: String,
    val currentPage: Int = 1,
    val activeTheme: ComfortTheme = ComfortTheme.DARK_ABYSS,
    val zoomLevel: Float = 1f,
    val scrollPosition: Float = 0f,
    val updatedAt: Long,
)

enum class ComfortTheme(val displayName: String, val emoji: String) {
    DARK_ABYSS("Dark Abyss", "🌑"),
    BOOK_PAPER("Book Paper", "📄"),
    SEPIA_SANDS("Sepia Sands", "🏜️"),
    FOCUS_PUNCH("Focus Punch", "🎯"),
    MONOCHROME("Monochrome", "⬛"),
    GARDEN_SAGE("Garden Sage", "🌿"),
}
