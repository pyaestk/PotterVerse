package com.project.potterverse.view.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.potterverse.data.repository.PotterRepository
import com.project.potterverse.model.CharacterDetails
import com.project.potterverse.model.CharacterDetailsData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsViewModel(
    private val potterRepository: PotterRepository
): ViewModel() {

    private var characterDetailsLiveData = MutableLiveData<CharacterDetailsData>()


    fun fetchCharacterDetails(id: String) {
        potterRepository.getCharacterDetails(id).enqueue(object: Callback<CharacterDetails>{
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

    fun insertChar(character: CharacterDetailsData) {
        viewModelScope.launch {
            potterRepository.insertUpdateCharacter(character)
        }
    }

    fun deleteChar(character: CharacterDetailsData) {
        viewModelScope.launch {
           potterRepository.deleteCharacter(character)
        }
    }

    fun getAllFavChar(): LiveData<List<CharacterDetailsData>> {
        return potterRepository.getFavCharacters()
    }

}

