package com.project.potterverse.di

import com.project.potterverse.data.service.PotterApi
import com.project.potterverse.utils.Constant
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<PotterApi> {
        get<Retrofit>().create(PotterApi::class.java)
    }
}