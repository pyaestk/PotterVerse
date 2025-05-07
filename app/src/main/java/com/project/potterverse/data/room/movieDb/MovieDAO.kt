package com.project.potterverse.data.room.movieDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.potterverse.data.movieDetails.MovieDetailData

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateMovie(movie: MovieDetailData)

    @Delete
    suspend fun deleteMovie(movie: MovieDetailData)

    @Query("SELECT * FROM movieInformation")
    fun getAllMovies(): LiveData<List<MovieDetailData>>
}