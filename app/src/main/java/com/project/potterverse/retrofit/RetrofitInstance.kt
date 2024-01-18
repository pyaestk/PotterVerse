package com.project.potterverse.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: PotterApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.potterdb.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PotterApi::class.java)
    }
}