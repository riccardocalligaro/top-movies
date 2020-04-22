package com.riccardocalligaro.imdbmovies.presentation

import com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    val presentationModule = module {
        viewModel {
            HomeViewModel(get())
        }
    }
}