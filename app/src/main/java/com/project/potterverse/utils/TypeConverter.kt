package com.project.potterverse.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.potterverse.model.CharacterDetailsAttributes
import com.project.potterverse.model.BookDetailsAttributes
import com.project.potterverse.model.BookDetailsRelationships
import com.project.potterverse.data.movieDetails.MovieDetailAttributes

@TypeConverters
class TypeConverter {

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

}