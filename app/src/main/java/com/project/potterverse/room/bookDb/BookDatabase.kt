package com.project.potterverse.room.bookDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.room.TypeConverter


@Database(entities = [BookDetailsData::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class BookDatabase: RoomDatabase(){

    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        var INSTANCE: BookDatabase? = null

        @Synchronized
        fun getInstance(context: Context): BookDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    BookDatabase::class.java,
                    "book.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as BookDatabase
        }
    }
}