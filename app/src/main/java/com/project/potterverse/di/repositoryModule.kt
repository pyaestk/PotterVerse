package com.project.potterverse.di

import com.project.potterverse.data.repository.PotterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        PotterRepository(get(), get())
    }
}