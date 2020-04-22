package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed

import androidx.lifecycle.viewModelScope
import com.riccardocalligaro.imdbmovies.base.generic.Status
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseAction
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseViewModel
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseViewState
import com.riccardocalligaro.imdbmovies.domain.model.FeedItemDomainModel
import com.riccardocalligaro.imdbmovies.domain.usecase.GetTopMoviesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getTopMoviesUseCase: GetTopMoviesUseCase
) : BaseViewModel<HomeViewModel.ViewState, HomeViewModel.Action>(
    ViewState()
) {

    init {
        getMoviesList()
    }

    override fun onLoadData() {
        getMoviesList()
    }

    private fun getMoviesList() {
        viewModelScope.launch {
            getTopMoviesUseCase.execute().collect {
                when (it.status) {
                    Status.LOADING -> {
                        sendAction(Action.MoviesFeedLoadingInProgress(it.data ?: listOf()))
                    }

                    Status.SUCCESS -> {
                        sendAction(Action.MoviesFeedLoadingSuccess(feedItems = it.data!!))
                    }

                    Status.ERROR -> {
                        sendAction(Action.MoviesFeedLoadingFailure)
                    }
                }
            }
        }
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

        is Action.MoviesFeedLoadingInProgress -> state.copy(
            isLoading = true,
            isError = false,
            feedItems = viewAction.feedItems
        )
    }


    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val feedItems: List<FeedItemDomainModel> = listOf()
    ) : BaseViewState

    sealed class Action : BaseAction {
        class MoviesFeedLoadingSuccess(val feedItems: List<FeedItemDomainModel>) : Action()
        object MoviesFeedLoadingFailure : Action()
        class MoviesFeedLoadingInProgress(val feedItems: List<FeedItemDomainModel>) : Action()
    }


}
