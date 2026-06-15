package com.beinganujchaudhary.aetherread.di

import android.content.Context
import androidx.room.Room
import com.beinganujchaudhary.aetherread.data.db.AetherReadDatabase
import com.beinganujchaudhary.aetherread.data.db.dao.AnnotationDao
import com.beinganujchaudhary.aetherread.data.db.dao.DocumentDao
import com.beinganujchaudhary.aetherread.data.db.dao.ReadingStateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AetherReadDatabase {
        return Room.databaseBuilder(
            context,
            AetherReadDatabase::class.java,
            "aetherread.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDocumentDao(database: AetherReadDatabase): DocumentDao {
        return database.documentDao()
    }

    @Provides
    fun provideAnnotationDao(database: AetherReadDatabase): AnnotationDao {
        return database.annotationDao()
    }

    @Provides
    fun provideReadingStateDao(database: AetherReadDatabase): ReadingStateDao {
        return database.readingStateDao()
    }
}
