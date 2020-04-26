package com.riccardocalligaro.imdbmovies.domain.usecase

import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.repository.MoviesRepository

class GetSavedMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend fun execute(): List<MovieDomainModel> {
        return moviesRepository.getSavedMovies()
    }
}