package com.riccardocalligaro.imdbmovies.domain.usecase

import com.riccardocalligaro.imdbmovies.base.generic.Resource
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetTopMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    fun execute(): Flow<Resource<List<MovieDomainModel>>> {
        return moviesRepository.getTopMovies()
    }
}