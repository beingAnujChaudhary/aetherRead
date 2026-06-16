package com.atherread.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfDao {
    @Query("SELECT * FROM pdfs ORDER BY lastOpened DESC")
    fun getAllPdfs(): Flow<List<PdfEntity>>

    @Query("SELECT * FROM pdfs WHERE path = :path")
    suspend fun getPdfByPath(path: String): PdfEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePdf(pdf: PdfEntity)

    @Query("UPDATE pdfs SET currentPage = :page WHERE path = :path")
    suspend fun updateCurrentPage(path: String, page: Int)

    @Query("UPDATE pdfs SET lastOpened = :timestamp WHERE path = :path")
    suspend fun updateLastOpened(path: String, timestamp: Long)
}
