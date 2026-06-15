package com.beinganujchaudhary.aetherread.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beinganujchaudhary.aetherread.data.db.entity.ReadingStateEntity

@Dao
interface ReadingStateDao {
    @Query("SELECT * FROM reading_states WHERE documentId = :documentId LIMIT 1")
    suspend fun getReadingState(documentId: String): ReadingStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(readingState: ReadingStateEntity)
}
