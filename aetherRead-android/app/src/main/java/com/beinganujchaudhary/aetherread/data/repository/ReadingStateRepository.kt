package com.beinganujchaudhary.aetherread.data.repository

import com.beinganujchaudhary.aetherread.data.db.dao.ReadingStateDao
import com.beinganujchaudhary.aetherread.data.db.entity.toDomain
import com.beinganujchaudhary.aetherread.data.db.entity.toEntity
import com.beinganujchaudhary.aetherread.domain.model.ReadingState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReadingStateRepository @Inject constructor(
    private val dao: ReadingStateDao
) {
    suspend fun getReadingState(documentId: String): ReadingState? {
        return dao.getReadingState(documentId)?.toDomain()
    }

    suspend fun saveReadingState(state: ReadingState) {
        dao.upsert(state.toEntity())
    }
}
