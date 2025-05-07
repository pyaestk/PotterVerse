package com.project.potterverse.data.room.movieDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.room.TypeConverter

@Database(entities = [MovieDetailData::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDAO

    companion object {
        @Volatile
        var INSTANCE: MovieDatabase? = null
        @Synchronized
        fun getInstance(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    "movie.db"
                ).build()
            }
            return INSTANCE as MovieDatabase
        }
    }
    
}