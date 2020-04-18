package com.riccardocalligaro.imdbmovies.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.riccardocalligaro.imdbmovies.base.generic.NetworkBoundResource
import com.riccardocalligaro.imdbmovies.base.generic.Resource
import com.riccardocalligaro.imdbmovies.data.local.dao.MovieDao
import com.riccardocalligaro.imdbmovies.data.local.entity.MovieLocalModel
import com.riccardocalligaro.imdbmovies.data.local.entity.toDomainModel
import com.riccardocalligaro.imdbmovies.data.remote.IMDbService
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import kotlin.time.ExperimentalTime
import kotlin.time.hours

class MoviesRepositoryImpl(
    private val movieDao: MovieDao,
    private val imDbService: IMDbService,
    private val sharedPreferences: SharedPreferences
) : MoviesRepository {

    companion object {
        @ExperimentalTime
        private val MOVIE_EXPIRY_IN_MILLIS = 1.hours.inMilliseconds.toLong()
        private const val KEY_LAST_SYNCED = "last_synced"
    }

    @ExperimentalTime
    override fun getTopMovies(): Flow<Resource<List<MovieDomainModel>>> {

        return object : NetworkBoundResource<List<MovieDomainModel>, List<MovieLocalModel>>() {
            override fun fetchFromLocal(): Flow<List<MovieDomainModel>> {
                Timber.i("Fetching from local...")

                return movieDao.getAllMovies().map {
                    it.map { local ->
                        local.toDomainModel()
                    }
                }
            }

            override suspend fun fetchFromRemote(): List<MovieLocalModel> {
                Timber.i("Fetching from remote...")
                return imDbService.getTopMovies()
            }

            override fun saveRemoteData(data: List<MovieLocalModel>) {
                Timber.i("Saving remote...")
                movieDao.insertAllMovies(data)
                sharedPreferences.edit {
                    putLong(KEY_LAST_SYNCED, System.currentTimeMillis())
                }
            }

            override fun shouldFetchFromRemote(data: List<MovieDomainModel>): Boolean {
                val lastSynced = sharedPreferences.getLong(KEY_LAST_SYNCED, -1)
                return lastSynced == -1L || data.isNullOrEmpty() || isExpired(lastSynced)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }


    @ExperimentalTime
    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= MOVIE_EXPIRY_IN_MILLIS
    }
}