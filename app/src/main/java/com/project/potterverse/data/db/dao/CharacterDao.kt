package com.project.potterverse.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.model.CharacterDetailsData

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateChar(character: CharacterDetailsData)

    @Delete
    suspend fun deleteChar(character: CharacterDetailsData)

    @Query("SELECT * FROM characterInformation")
    fun getAllCharacters(): LiveData<List<CharacterDetailsData>>

    @Query("DELETE FROM characterInformation")
    suspend fun deleteAllCharacters()
}