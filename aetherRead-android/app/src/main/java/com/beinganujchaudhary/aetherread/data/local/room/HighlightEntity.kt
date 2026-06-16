package com.beinganujchaudhary.aetherread.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "highlights")
data class HighlightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pdfPath: String,
    val pageNumber: Int,
    val selectedText: String,
    val color: Long
)
