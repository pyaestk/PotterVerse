package com.project.potterverse.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.potterverse.data.movieDetails.MovieDetailAttributes
import com.project.potterverse.data.movies.MovieAttributes

@TypeConverters
class TypeConverter {

    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromMovieAttributes(movieAttributes: MovieDetailAttributes?): String? {
        return Gson().toJson(movieAttributes)
    }

    @TypeConverter
    fun toMovieAttributes(value: String?): MovieDetailAttributes? {
        val type = object : TypeToken<MovieDetailAttributes?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromBoolean(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value != 0
    }
}