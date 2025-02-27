package com.project.potterverse.room.characterDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.room.TypeConverter

@Database(entities = [CharacterDetailsData::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class CharacterDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        var INSTANCE: CharacterDatabase? = null
        @Synchronized
        fun getInstance(context: Context): CharacterDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    CharacterDatabase::class.java,
                    "character.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as CharacterDatabase
        }
    }

}