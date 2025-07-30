package com.takehomechallenge.saputra.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.takehomechallenge.saputra.data.db.AppDatabase
import com.takehomechallenge.saputra.data.db.FavoriteCharacterDao
import com.takehomechallenge.saputra.data.db.SearchHistoryDao
import com.takehomechallenge.saputra.data.repository.FavoriteRepositoryImpl
import com.takehomechallenge.saputra.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
                CREATE TABLE IF NOT EXISTS `favorite_characters` (
                    `id` INTEGER NOT NULL,
                    `name` TEXT NOT NULL,
                    `status` TEXT NOT NULL,
                    `species` TEXT NOT NULL,
                    `type` TEXT NOT NULL,
                    `gender` TEXT NOT NULL,
                    `originName` TEXT NOT NULL,
                    `originUrl` TEXT NOT NULL,
                    `locationName` TEXT NOT NULL,
                    `locationUrl` TEXT NOT NULL,
                    `image` TEXT NOT NULL,
                    `episodes` TEXT NOT NULL,
                    `url` TEXT NOT NULL,
                    `created` TEXT NOT NULL,
                    `timestamp` INTEGER NOT NULL,
                    PRIMARY KEY(`id`)
                )
            """.trimIndent())
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty_db"
        )
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteCharacterDao: FavoriteCharacterDao
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteCharacterDao)
    }

    @Provides
    @Singleton
    fun provideSearchHistoryDao(
        database: AppDatabase
    ): SearchHistoryDao = database.searchHistoryDao()

    @Provides
    @Singleton
    fun provideFavoriteCharacterDao(
        database: AppDatabase
    ): FavoriteCharacterDao = database.favoriteCharacterDao()
} 