package com.atherread.core.di

import android.content.Context
import androidx.room.Room
import com.atherread.data.local.room.AetherReadDatabase
import com.atherread.data.local.room.PdfDao
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
    fun provideAetherReadDatabase(
        @ApplicationContext context: Context
    ): AetherReadDatabase {
        return Room.databaseBuilder(
            context,
            AetherReadDatabase::class.java,
            "aetherread_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePdfDao(database: AetherReadDatabase): PdfDao {
        return database.pdfDao()
    }
}
