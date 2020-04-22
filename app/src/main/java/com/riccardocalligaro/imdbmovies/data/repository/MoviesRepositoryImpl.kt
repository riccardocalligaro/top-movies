package com.riccardocalligaro.imdbmovies.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.riccardocalligaro.imdbmovies.base.generic.NetworkBoundResource
import com.riccardocalligaro.imdbmovies.base.generic.Resource
import com.riccardocalligaro.imdbmovies.data.local.dao.MovieDao
import com.riccardocalligaro.imdbmovies.data.local.entity.MovieLocalModel
import com.riccardocalligaro.imdbmovies.data.local.entity.toDomainModel
import com.riccardocalligaro.imdbmovies.data.remote.IMDbService
import com.riccardocalligaro.imdbmovies.domain.model.FeedItemDomainModel
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
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
    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun getTopMovies(): Flow<Resource<List<FeedItemDomainModel>>> {
        return object : NetworkBoundResource<List<MovieLocalModel>, List<FeedItemDomainModel>>() {

            override fun shouldFetch(data: List<FeedItemDomainModel>): Boolean {
                return shouldFetchFromRemote(data)
            }

            override fun query(): Flow<List<FeedItemDomainModel>> {
                Timber.i("Quering from database")
                return movieDao.getAllMovies().map {
                    convertToFeed(it)
                }
            }

            override suspend fun fetch(): List<MovieLocalModel> {
                Timber.i("Fetching from remote")
                return imDbService.getTopMovies()
            }

            override suspend fun saveFetchResult(data: List<MovieLocalModel>) {
                sharedPreferences.edit {
                    putLong(KEY_LAST_SYNCED, System.currentTimeMillis())
                }
                return movieDao.insertAllMovies(data)
            }

        }.asFlow()
    }


    @ExperimentalTime
    private fun shouldFetchFromRemote(movies: List<FeedItemDomainModel>): Boolean {
        val lastSynced: Long = sharedPreferences.getLong(KEY_LAST_SYNCED, -1)
        return lastSynced == -1L ||
                movies.isNullOrEmpty() ||
                isExpired(lastSynced)
    }


    @ExperimentalTime
    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= MOVIE_EXPIRY_IN_MILLIS
    }


    private fun convertToFeed(movies: List<MovieLocalModel>): List<FeedItemDomainModel> {
        val genreSet = mutableSetOf<String>()
        for (movie in movies) {
            for (genre in movie.genres) {
                genreSet.add(genre)
            }
        }
        val feedItems = mutableListOf<FeedItemDomainModel>()
        for ((index, genre) in genreSet.withIndex()) {
            val genreMovies: List<MovieDomainModel> = movies
                .filter { it.genres.contains(genre) }.map {
                    it.toDomainModel()
                }
            feedItems.add(FeedItemDomainModel(index.toLong(), genre, genreMovies.shuffled()))
        }

        return feedItems

    }


}