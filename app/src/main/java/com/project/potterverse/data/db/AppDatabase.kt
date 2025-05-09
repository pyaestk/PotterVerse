package com.project.potterverse.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.CharacterDetailsData
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.db.dao.BookDao
import com.project.potterverse.data.db.dao.CharacterDao
import com.project.potterverse.data.db.dao.MovieDAO
import com.project.potterverse.utils.TypeConverter

@Database(
    entities = [
        CharacterDetailsData::class,
        BookDetailsData::class,
        MovieDetailData::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun bookDao(): BookDao
    abstract fun movieDao(): MovieDAO

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null
        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "character.db"
                ).build()
            }
            return INSTANCE as AppDatabase
        }
    }

}