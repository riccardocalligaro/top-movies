package com.riccardocalligaro.imdbmovies.domain.model

data class FeedItemDomainModel(
    val id: Long,
    val genre: String,
    val movies: List<MovieDomainModel>
)