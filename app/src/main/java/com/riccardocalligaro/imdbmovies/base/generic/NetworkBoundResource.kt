package com.riccardocalligaro.imdbmovies.base.generic

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*


abstract class NetworkBoundResource<DATA, DOMAIN> {

    @FlowPreview
    @ExperimentalCoroutinesApi
    fun asFlow(): Flow<Resource<DOMAIN>> = flow {
        val flow = query()
            .onStart { emit(Resource.loading<DOMAIN>(null)) }
            .flatMapConcat { data ->
                if (shouldFetch(data)) {
                    emit(Resource.loading(data))
                    try {
                        saveFetchResult(fetch())
                        query().map { Resource.success(it) }
                    } catch (throwable: Throwable) {
                        onFetchFailed(throwable)
                        query().map { Resource.error(throwable.message!!, it) }
                    }
                } else {
                    query().map { Resource.success(it) }
                }
            }
        emitAll(flow)
    }

    abstract fun query(): Flow<DOMAIN>

    abstract suspend fun fetch(): DATA

    abstract suspend fun saveFetchResult(data: DATA)

    open fun onFetchFailed(throwable: Throwable) = Unit

    open fun shouldFetch(data: DOMAIN) = true
}
