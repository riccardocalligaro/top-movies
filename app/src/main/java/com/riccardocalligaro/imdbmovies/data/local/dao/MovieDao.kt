package com.riccardocalligaro.imdbmovies.data.local.dao

import androidx.room.*
import com.riccardocalligaro.imdbmovies.data.local.entity.MovieLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("select * from movies")
    fun getAllMovies(): Flow<List<MovieLocalModel>>


    @Query("select * from movies where saved = 1")
    suspend fun getSavedMovies(): List<MovieLocalModel>

    @Query("select * from movies where saved = 1")
    fun getSavedMoviesFlow(): Flow<List<MovieLocalModel>>

    @Query("UPDATE movies SET saved=1 WHERE id in (:ids) ")
    suspend fun updateSavedMovies(ids: List<Long>)

    @Update
    suspend fun updateMovie(movie: MovieLocalModel)


    @Query("UPDATE movies SET saved = 1 WHERE id = :id")
    suspend fun saveMovie(id: Long)

    @Query("UPDATE movies SET saved = 0 WHERE id = :id")
    suspend fun discardMovie(id: Long)


    @Query("delete from movies")
    suspend fun deleteAllMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieLocalModel>)
}