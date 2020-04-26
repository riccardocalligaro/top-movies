package com.riccardocalligaro.imdbmovies.data.remote

import com.riccardocalligaro.imdbmovies.data.remote.model.MovieRemoteModel
import retrofit2.http.GET

interface IMDbService {
    @GET("top_250_min.json")
    suspend fun getTopMovies(): List<MovieRemoteModel>
}