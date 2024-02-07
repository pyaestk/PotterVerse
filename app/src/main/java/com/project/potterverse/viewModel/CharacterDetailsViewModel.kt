package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.potterverse.data.CharacterDetails.CharacterDetails
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.retrofit.RetrofitInstance
import com.project.potterverse.room.bookDb.BookDatabase
import com.project.potterverse.room.characterDb.CharacterDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsViewModel(private val charDatabase: CharacterDatabase): ViewModel() {

    private var characterDetailsLiveData = MutableLiveData<CharacterDetailsData>()
    private var favCharLiveData = charDatabase.characterDao().getAllCharacters()

    fun fetchCharacterDetails(id: String) {
        RetrofitInstance.api.getCharacterDetails(id).enqueue(object: Callback<CharacterDetails>{
            override fun onResponse(
                call: Call<CharacterDetails>,
                response: Response<CharacterDetails>
            ) {
                response.body()?.let {
                    characterDetailsLiveData.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<CharacterDetails>, t: Throwable) {
                Log.e("Error Fetching on Character Details", t.message.toString())
            }

        })
    }

    fun getCharacterDetails(): LiveData<CharacterDetailsData>{
        return characterDetailsLiveData
    }

    fun insertChar(char: CharacterDetailsData) {
        viewModelScope.launch {
            charDatabase.characterDao().insertUpdateChar(char)
        }
    }

    fun deleteChar(char: CharacterDetailsData) {
        viewModelScope.launch {
            charDatabase.characterDao().deleteChar(char)
        }
    }

    fun getAllChars(): LiveData<List<CharacterDetailsData>> {
        return favCharLiveData
    }
}

class CharacterDetailViewModelFactory(private val charDatabase: CharacterDatabase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailsViewModel(charDatabase) as T
    }
}