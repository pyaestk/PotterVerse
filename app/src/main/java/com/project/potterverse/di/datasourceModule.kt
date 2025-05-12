package com.project.potterverse.di

import com.project.potterverse.data.datasource.PotterLocalDatasource
import com.project.potterverse.data.datasource.PotterRemoteDataSource
import org.koin.dsl.module

val datasourceModule = module {
    single {
        PotterLocalDatasource(get())
    }
    single {
        PotterRemoteDataSource(get())
    }
}