package com.atherread.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pdfs")
data class PdfEntity(
    @PrimaryKey
    val path: String,
    val name: String,
    val lastOpened: Long,
    val totalPages: Int,
    val currentPage: Int,
    val isFavorite: Boolean
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pdfPath: String,
    val pageNumber: Int,
    val note: String?
)

@Entity(tableName = "highlights")
data class HighlightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pdfPath: String,
    val pageNumber: Int,
    val selectedText: String,
    val color: Long
)
