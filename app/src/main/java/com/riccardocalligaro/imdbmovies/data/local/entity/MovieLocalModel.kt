package com.riccardocalligaro.imdbmovies.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.squareup.moshi.Json
import java.io.Serializable


@Entity(
    tableName = "movies",
    indices = [
        Index("imdbUrl", unique = true)
    ]
)
data class MovieLocalModel(
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
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}


internal fun MovieLocalModel.toDomainModel(): MovieDomainModel {
    return MovieDomainModel(
        id, actors, description, directors, genres, imageUrl, thumbUrl, imdbUrl, name, rating
    )
}