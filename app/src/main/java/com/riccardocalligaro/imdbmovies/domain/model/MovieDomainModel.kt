package com.riccardocalligaro.imdbmovies.domain.model

data class MovieDomainModel(
    val id: Long,
    val actors: List<String>,
    val description: String,
    val directors: List<String>,
    val genres: List<String>,
    val imageUrl: String,
    val thumbUrl: String,
    val imdbUrl: String,
    val name: String,
    val rating: Float
)