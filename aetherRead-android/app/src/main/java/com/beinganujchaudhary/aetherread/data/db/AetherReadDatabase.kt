package com.beinganujchaudhary.aetherread.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beinganujchaudhary.aetherread.data.db.dao.AnnotationDao
import com.beinganujchaudhary.aetherread.data.db.dao.DocumentDao
import com.beinganujchaudhary.aetherread.data.db.dao.ReadingStateDao
import com.beinganujchaudhary.aetherread.data.db.entity.AnnotationEntity
import com.beinganujchaudhary.aetherread.data.db.entity.DocumentEntity
import com.beinganujchaudhary.aetherread.data.db.entity.ReadingStateEntity

@Database(
    entities = [
        DocumentEntity::class,
        AnnotationEntity::class,
        ReadingStateEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AetherReadDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun annotationDao(): AnnotationDao
    abstract fun readingStateDao(): ReadingStateDao
}
