package com.riccardocalligaro.imdbmovies.base.generic

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class NetworkBoundResource<DB, REMOTE> {

    @MainThread
    abstract fun fetchFromLocal(): Flow<DB>

    @MainThread
    abstract suspend fun fetchFromRemote(): REMOTE

    @WorkerThread
    abstract fun saveRemoteData(data: REMOTE)

    @MainThread
    abstract fun shouldFetchFromRemote(data: DB): Boolean

    @ExperimentalCoroutinesApi
    fun asFlow() = flow<Resource<DB>> {

        Timber.d("Starting...")
        // sending loading status
        Timber.d("Sending loading...")
        emit(Resource.loading())

        val localData = fetchFromLocal().first()

        // checking if local data is staled
        if (shouldFetchFromRemote(localData)) {
            try {
                val fetchedData = fetchFromRemote()
                saveRemoteData(fetchedData)
                emitLocalDbData()
            } catch (throwable: Throwable) {
                Timber.e(throwable)
                emit(Resource.error(throwable.message!!))
            }

        } else {
            Timber.i("Fetching from local")
            // valid cache, no need to fetch from remote.
            emitLocalDbData()
        }
    }

    @ExperimentalCoroutinesApi
    private suspend fun FlowCollector<Resource<DB>>.emitLocalDbData() {
        Timber.i("Sending local data to UI")
        emitAll(fetchFromLocal().map { dbData ->
            Timber.i("Sending local...")
            Resource.success(dbData)
        })
    }
}