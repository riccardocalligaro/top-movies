package com.riccardocalligaro.imdbmovies.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.riccardocalligaro.imdbmovies.data.local.entity.MovieLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("select * from movies")
    fun getAllMovies(): Flow<List<MovieLocalModel>>

    @Query("delete from movies")
    fun deleteAllMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(movies: List<MovieLocalModel>)
}