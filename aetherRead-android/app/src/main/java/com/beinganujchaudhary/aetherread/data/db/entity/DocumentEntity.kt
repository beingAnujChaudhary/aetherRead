package com.beinganujchaudhary.aetherread.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beinganujchaudhary.aetherread.domain.model.Document

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey val id: String,
    val title: String,
    val author: String?,
    val pageCount: Int,
    val fileSizeBytes: Long,
    val fileHash: String,
    val localFilePath: String,
    val coverPageUri: String?,
    val uploadedAt: Long,
    val lastOpenedAt: Long?,
    val totalPagesRead: Int,
)

fun DocumentEntity.toDomain() = Document(
    id = id,
    title = title,
    author = author,
    pageCount = pageCount,
    fileSizeBytes = fileSizeBytes,
    fileHash = fileHash,
    localFilePath = localFilePath,
    coverPageUri = coverPageUri,
    uploadedAt = uploadedAt,
    lastOpenedAt = lastOpenedAt,
    totalPagesRead = totalPagesRead,
)

fun Document.toEntity() = DocumentEntity(
    id = id,
    title = title,
    author = author,
    pageCount = pageCount,
    fileSizeBytes = fileSizeBytes,
    fileHash = fileHash,
    localFilePath = localFilePath,
    coverPageUri = coverPageUri,
    uploadedAt = uploadedAt,
    lastOpenedAt = lastOpenedAt,
    totalPagesRead = totalPagesRead,
)
