package com.project.potterverse.di

import com.project.potterverse.view.viewModel.BookDetailsViewModel
import com.project.potterverse.view.viewModel.CharacterDetailsViewModel
import com.project.potterverse.view.viewModel.MainViewModel
import com.project.potterverse.view.viewModel.MovieDetailsViewModel
import com.project.potterverse.view.viewModel.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        BookDetailsViewModel(get())
    }
    viewModel {
        CharacterDetailsViewModel(get())
    }
    viewModel {
        MovieDetailsViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        MainViewModel(get())
    }
}