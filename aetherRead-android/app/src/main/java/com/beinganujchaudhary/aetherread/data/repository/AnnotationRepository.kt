package com.beinganujchaudhary.aetherread.data.repository

import com.beinganujchaudhary.aetherread.data.db.dao.AnnotationDao
import com.beinganujchaudhary.aetherread.data.db.entity.toDomain
import com.beinganujchaudhary.aetherread.data.db.entity.toEntity
import com.beinganujchaudhary.aetherread.domain.model.Annotation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnnotationRepository @Inject constructor(
    private val dao: AnnotationDao
) {
    fun getAnnotationsForDocument(documentId: String): Flow<List<Annotation>> {
        return dao.getAnnotationsForDocument(documentId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun saveAnnotation(annotation: Annotation) {
        dao.insert(annotation.toEntity())
    }

    suspend fun updateAnnotation(annotation: Annotation) {
        dao.update(annotation.toEntity())
    }

    suspend fun deleteAnnotation(id: String) {
        dao.softDelete(id)
    }
}
