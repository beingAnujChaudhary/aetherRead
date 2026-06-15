package com.beinganujchaudhary.aetherread.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beinganujchaudhary.aetherread.domain.model.ComfortTheme
import com.beinganujchaudhary.aetherread.domain.model.ReadingState

@Entity(tableName = "reading_states")
data class ReadingStateEntity(
    @PrimaryKey val documentId: String,
    val currentPage: Int,
    val activeTheme: String, // Stored as String
    val zoomLevel: Float,
    val scrollPosition: Float,
    val updatedAt: Long,
)

fun ReadingStateEntity.toDomain() = ReadingState(
    documentId = documentId,
    currentPage = currentPage,
    activeTheme = ComfortTheme.valueOf(activeTheme),
    zoomLevel = zoomLevel,
    scrollPosition = scrollPosition,
    updatedAt = updatedAt,
)

fun ReadingState.toEntity() = ReadingStateEntity(
    documentId = documentId,
    currentPage = currentPage,
    activeTheme = activeTheme.name,
    zoomLevel = zoomLevel,
    scrollPosition = scrollPosition,
    updatedAt = updatedAt,
)
