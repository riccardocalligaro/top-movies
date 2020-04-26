package com.riccardocalligaro.imdbmovies.domain.usecase

import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.repository.MoviesRepository

class DiscardMovieUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend fun execute(movie: MovieDomainModel) {
        return moviesRepository.discardMovie(movie)
    }
}