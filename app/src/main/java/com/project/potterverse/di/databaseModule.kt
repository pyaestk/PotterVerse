package com.project.potterverse.di

import android.content.Context
import androidx.room.Room
import com.project.potterverse.data.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        provideRoomDatabase(context = androidContext())
    }
    single {
        provideMovieDao(
            database = get()
        )
    }
    single {
        provideBookDao(
            database = get()
        )
    }
    single {
        provideCharacterDao(
            database = get()
        )
    }


}

private fun provideRoomDatabase(context: Context): AppDatabase {

    val database: AppDatabase?

    database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "potter-verse.db"
    ).build()

    return database
}

private fun provideCharacterDao(database: AppDatabase) = database.characterDao()
private fun provideBookDao(database: AppDatabase) = database.bookDao()
private fun provideMovieDao(database: AppDatabase) = database.movieDao()