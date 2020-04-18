package com.riccardocalligaro.imdbmovies.domain.repository

import com.riccardocalligaro.imdbmovies.base.generic.Resource
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getTopMovies(): Flow<Resource<List<MovieDomainModel>>>
}