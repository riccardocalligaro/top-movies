package com.riccardocalligaro.imdbmovies.domain

import com.riccardocalligaro.imdbmovies.data.repository.MoviesRepositoryImpl
import com.riccardocalligaro.imdbmovies.domain.repository.MoviesRepository
import com.riccardocalligaro.imdbmovies.domain.usecase.GetTopMoviesUseCase
import org.koin.dsl.module

object DomainModule {
    val domainModule = module {
        single<MoviesRepository> { MoviesRepositoryImpl(get(), get(), get()) }
        factory { GetTopMoviesUseCase(get()) }
    }
}