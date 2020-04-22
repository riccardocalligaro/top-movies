package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed.model

import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel

data class FeedItem(
    val id: Long,
    val genre: String,
    val movies: List<MovieDomainModel>
)