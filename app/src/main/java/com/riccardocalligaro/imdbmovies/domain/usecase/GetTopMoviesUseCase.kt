package com.riccardocalligaro.imdbmovies.domain.usecase

import com.riccardocalligaro.imdbmovies.base.generic.Resource
import com.riccardocalligaro.imdbmovies.domain.model.FeedItemDomainModel
import com.riccardocalligaro.imdbmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetTopMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    fun execute(): Flow<Resource<List<FeedItemDomainModel>>> {
        return moviesRepository.getTopMovies()
    }
}