package com.riccardocalligaro.imdbmovies.presentation

import com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.detail.MovieDetailsViewModel
import com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed.HomeViewModel
import com.riccardocalligaro.imdbmovies.presentation.feature.saved.SavedViewModel
import com.riccardocalligaro.imdbmovies.presentation.feature.saved.recyclerview.SavedMovieAdapter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    val presentationModule = module {
        viewModel {
            HomeViewModel(get())
        }

        viewModel {
            MovieDetailsViewModel(get(), get())
        }

        viewModel {
            SavedViewModel(get())
        }

        single { SavedMovieAdapter() }
    }
}