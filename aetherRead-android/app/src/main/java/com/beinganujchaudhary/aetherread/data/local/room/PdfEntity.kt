package com.beinganujchaudhary.aetherread.data.local.room

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
