package com.project.potterverse.room

import androidx.room.Database
import androidx.room.TypeConverters
import com.project.potterverse.data.movieDetails.MovieDetailData

@Database(entities = [MovieDetailData::class], version = 1)
abstract class MovieDatabase {
}