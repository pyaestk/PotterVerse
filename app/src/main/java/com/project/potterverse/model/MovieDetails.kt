package com.project.potterverse.data.movieDetails

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MovieDetailAttributes(
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
@Entity("movieInformation")
data class MovieDetailData(
    val attributes: MovieDetailAttributes,
    @PrimaryKey
    val id: String,
    val type: String,
    var bookmark: Boolean,
)
data class MovieDetails(
    val data: MovieDetailData,
)

data class MovieList(
    val data: List<MovieDetailData>,
)