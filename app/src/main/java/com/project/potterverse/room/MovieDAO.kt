package com.project.potterverse.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.data.movies.MovieData

interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateMovie(movie: MovieDetailData)

    @Delete
    suspend fun deleteMeal(movie: MovieDetailData)

    @Query("SELECT * FROM movieInformation")
    fun getAllMeals(): LiveData<List<MovieDetailData>>
}