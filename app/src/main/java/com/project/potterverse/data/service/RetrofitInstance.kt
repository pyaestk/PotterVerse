package com.project.potterverse.data.service

import com.project.potterverse.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: PotterApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PotterApi::class.java)
    }
}