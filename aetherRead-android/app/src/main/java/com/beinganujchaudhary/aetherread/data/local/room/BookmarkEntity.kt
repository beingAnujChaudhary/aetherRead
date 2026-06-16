package com.beinganujchaudhary.aetherread.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pdfPath: String,
    val pageNumber: Int,
    val note: String?
)
