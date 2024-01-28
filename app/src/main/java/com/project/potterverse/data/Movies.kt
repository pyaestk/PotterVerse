package com.project.potterverse.data.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieAttributes(
    val box_office: String?,
    val budget: String?,
    val cinematographers: List<String>?,
    val directors: List<String>?,
    val distributors: List<String>?,
    val editors: List<String>?,
    val music_composers: List<String>?,
    val poster: String?,
    val producers: List<String>?,
    val rating: String?,
    val release_date: String?,
    val running_time: String?,
    val screenwriters: List<String>?,
    val slug: String?,
    val summary: String?,
    val title: String?,
    val trailer: String?,
    val wiki: String?
)


data class MovieData(
    val attributes: MovieAttributes,
    @PrimaryKey
    val id: String,
    val type: String?,
    val bookmarked: Boolean
)
data class MovieList(
    val data: List<MovieData>,
)
