package com.project.potterverse.room.characterDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.bookDetails.BookDetailsData

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateChar(character: CharacterDetailsData)

    @Delete
    suspend fun deleteChar(character: CharacterDetailsData)

    @Query("SELECT * FROM characterInformation")
    fun getAllCharacters(): LiveData<List<CharacterDetailsData>>
}