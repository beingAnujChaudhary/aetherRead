package com.atherread.data.repository

import com.atherread.data.local.room.PdfDao
import com.atherread.data.local.room.PdfEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PdfRepository @Inject constructor(
    private val pdfDao: PdfDao
) {
    fun getAllPdfs(): Flow<List<PdfEntity>> = pdfDao.getAllPdfs()
    suspend fun getPdfByPath(path: String): PdfEntity? = pdfDao.getPdfByPath(path)
    suspend fun insertOrUpdatePdf(pdf: PdfEntity) = pdfDao.insertOrUpdatePdf(pdf)
    suspend fun updateCurrentPage(path: String, page: Int) = pdfDao.updateCurrentPage(path, page)
    suspend fun updateLastOpened(path: String, timestamp: Long) = pdfDao.updateLastOpened(path, timestamp)
}
