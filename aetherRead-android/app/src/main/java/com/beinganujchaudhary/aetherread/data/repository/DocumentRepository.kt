package com.beinganujchaudhary.aetherread.data.repository

import com.beinganujchaudhary.aetherread.data.db.dao.DocumentDao
import com.beinganujchaudhary.aetherread.data.db.entity.toDomain
import com.beinganujchaudhary.aetherread.data.db.entity.toEntity
import com.beinganujchaudhary.aetherread.domain.model.Document
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepository @Inject constructor(
    private val dao: DocumentDao
) {
    fun getAllDocuments(): Flow<List<Document>> {
        return dao.getAllDocuments().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getDocument(id: String): Document? {
        return dao.getDocument(id)?.toDomain()
    }

    suspend fun findByHash(hash: String): Document? {
        return dao.findByHash(hash)?.toDomain()
    }

    suspend fun insertDocument(document: Document) {
        dao.insert(document.toEntity())
    }

    suspend fun updateDocument(document: Document) {
        dao.update(document.toEntity())
    }

    suspend fun deleteDocument(id: String) {
        dao.deleteById(id)
    }
}
