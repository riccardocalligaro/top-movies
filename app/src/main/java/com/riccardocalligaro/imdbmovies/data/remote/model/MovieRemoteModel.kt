package com.riccardocalligaro.imdbmovies.data.remote.model

import com.riccardocalligaro.imdbmovies.data.local.entity.MovieLocalModel
import com.squareup.moshi.Json

data class MovieRemoteModel(
    val actors: List<String>,
    @Json(name = "desc")
    val description: String,
    val directors: List<String>,
    @Json(name = "genre")
    val genres: List<String>,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "thumb_url")
    val thumbUrl: String,
    @Json(name = "imdb_url")
    val imdbUrl: String,
    val name: String,
    val rating: Float
)

internal fun MovieRemoteModel.toLocalModel(): MovieLocalModel {
    return MovieLocalModel(
        actors, description, directors, genres, imageUrl, thumbUrl, imdbUrl, name, rating, false
    )
}