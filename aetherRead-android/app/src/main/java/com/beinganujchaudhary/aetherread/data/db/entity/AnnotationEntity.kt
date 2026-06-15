package com.beinganujchaudhary.aetherread.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beinganujchaudhary.aetherread.domain.model.Annotation
import com.beinganujchaudhary.aetherread.domain.model.AnnotationCategory
import com.beinganujchaudhary.aetherread.domain.model.AnnotationType

@Entity(tableName = "annotations")
data class AnnotationEntity(
    @PrimaryKey val id: String,
    val documentId: String,
    val pageNumber: Int,
    val category: String, // Stored as String
    val note: String,
    val quote: String?,
    val annotationType: String?, // Stored as String
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean,
)

fun AnnotationEntity.toDomain() = Annotation(
    id = id,
    documentId = documentId,
    pageNumber = pageNumber,
    category = AnnotationCategory.valueOf(category),
    note = note,
    quote = quote,
    annotationType = annotationType?.let { AnnotationType.valueOf(it) },
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDeleted = isDeleted,
)

fun Annotation.toEntity() = AnnotationEntity(
    id = id,
    documentId = documentId,
    pageNumber = pageNumber,
    category = category.name,
    note = note,
    quote = quote,
    annotationType = annotationType?.name,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isDeleted = isDeleted,
)
