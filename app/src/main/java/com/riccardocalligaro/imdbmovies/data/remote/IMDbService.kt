package com.riccardocalligaro.imdbmovies.data.remote

import com.riccardocalligaro.imdbmovies.data.local.entity.MovieLocalModel
import retrofit2.http.GET

interface IMDbService {
    @GET("top_250_min.json")
    suspend fun getTopMovies(): List<MovieLocalModel>
}