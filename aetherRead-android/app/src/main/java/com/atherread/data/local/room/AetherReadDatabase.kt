package com.atherread.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PdfEntity::class, BookmarkEntity::class, HighlightEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AetherReadDatabase : RoomDatabase() {
    abstract fun pdfDao(): PdfDao
}
