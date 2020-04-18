package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard

import androidx.lifecycle.viewModelScope
import com.riccardocalligaro.imdbmovies.base.generic.Resource
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseAction
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseViewModel
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseViewState
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.usecase.GetTopMoviesUseCase
import com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.model.FeedItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getTopMoviesUseCase: GetTopMoviesUseCase
) : BaseViewModel<HomeViewModel.ViewState, HomeViewModel.Action>(ViewState()) {

    override fun onLoadData() {
        getFeedItemsList()
    }

    private fun getFeedItemsList() {
        viewModelScope.launch {
            getTopMoviesUseCase.execute().collect {
                when (it.status) {
                    Resource.Status.ERROR -> {
                        sendAction(Action.MoviesFeedLoadingFailure)
                    }
                    Resource.Status.SUCCESS -> {
                        val items = it.data!!
                        val feedItems = convertToFeed(items)
                        sendAction(Action.MoviesFeedLoadingSuccess(feedItems))
                    }
                }
            }

        }
    }

    private fun convertToFeed(movies: List<MovieDomainModel>): List<FeedItem> {

        val genreSet = mutableSetOf<String>()
        for (movie in movies) {
            for (genre in movie.genres) {
                genreSet.add(genre)
            }
        }
        val feedItems = mutableListOf<FeedItem>()
        for ((index, genre) in genreSet.withIndex()) {
            val genreMovies = movies
                .filter { it.genres.contains(genre) }
            feedItems.add(FeedItem(index.toLong(), genre, genreMovies.shuffled()))
        }
        return feedItems
    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.MoviesFeedLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            feedItems = viewAction.feedItems
        )

        is Action.MoviesFeedLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            feedItems = listOf()
        )
    }


    internal data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val feedItems: List<FeedItem> = listOf()
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class MoviesFeedLoadingSuccess(val feedItems: List<FeedItem>) : Action()
        object MoviesFeedLoadingFailure : Action()
    }


}
