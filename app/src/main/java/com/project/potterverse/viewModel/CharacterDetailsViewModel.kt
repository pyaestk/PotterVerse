package com.project.potterverse.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.potterverse.data.CharacterDetails.CharacterDetails
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsViewModel: ViewModel() {

    private var characterDetailsLiveData = MutableLiveData<CharacterDetailsData>()

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
}