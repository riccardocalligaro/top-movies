package com.riccardocalligaro.imdbmovies.domain.model

import android.os.Parcelable
import com.riccardocalligaro.imdbmovies.data.local.entity.MovieLocalModel
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    val rating: Float,
    val saved: Boolean
) : Parcelable

internal fun MovieDomainModel.toLocalModel(): MovieLocalModel {
    return MovieLocalModel(
        actors,
        description,
        directors,
        genres,
        imageUrl,
        thumbUrl,
        imdbUrl,
        name,
        rating
    )
}