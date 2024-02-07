package com.project.potterverse.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.potterverse.data.CharacterDetails.CharacterDetailsAttributes
import com.project.potterverse.data.bookDetails.BookDetailsAttributes
import com.project.potterverse.data.bookDetails.BookDetailsRelationships
import com.project.potterverse.data.movieDetails.MovieDetailAttributes

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
    fun fromInt(value: Int?): String? {
        return value?.toString()
    }
    fun toInt(value: String?): Int? {
        return value?.toInt()
    }

    //for movie
    @TypeConverter
    fun fromMovieAttributes(movieAttributes: MovieDetailAttributes?): String? {
        return Gson().toJson(movieAttributes)
    }

    @TypeConverter
    fun toMovieAttributes(value: String?): MovieDetailAttributes? {
        val type = object : TypeToken<MovieDetailAttributes?>() {}.type
        return Gson().fromJson(value, type)
    }

    //for book
    @TypeConverter
    fun fromBookAttributes(bookAttributes: BookDetailsAttributes?): String? {
        return Gson().toJson(bookAttributes)
    }

    @TypeConverter
    fun toBookAttributes(value: String?): BookDetailsAttributes? {
        val type = object : TypeToken<BookDetailsAttributes?>() {}.type
        return Gson().fromJson(value, type)
    }
    @TypeConverter
    fun fromBookDetailsRelationships(bookDetailsRelationships: BookDetailsRelationships?): String? {
        return Gson().toJson(bookDetailsRelationships)
    }

    @TypeConverter
    fun toBookDetailsRelationships(value: String?): BookDetailsRelationships? {
        val type = object : TypeToken<BookDetailsRelationships?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCharacterDetailsAttributes(charAttributes: CharacterDetailsAttributes?): String? {
        return Gson().toJson(charAttributes)
    }
    @TypeConverter
    fun toCharacterDetailsAttributes(value: String?): CharacterDetailsAttributes? {
        val type = object : TypeToken<CharacterDetailsAttributes?>() {}.type
        return Gson().fromJson(value, type)
    }



    @TypeConverter
    fun fromBoolean(value: Boolean?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toBoolean(value: String?): Boolean? {
        return value?.toBoolean()
    }

}