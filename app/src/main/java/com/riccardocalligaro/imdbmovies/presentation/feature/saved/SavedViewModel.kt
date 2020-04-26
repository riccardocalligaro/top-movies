package com.riccardocalligaro.imdbmovies.presentation.feature.saved

import androidx.lifecycle.viewModelScope
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseAction
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseViewModel
import com.riccardocalligaro.imdbmovies.base.generic.viewmodel.BaseViewState
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.usecase.GetSavedMoviesUseCase
import kotlinx.coroutines.launch

class SavedViewModel(
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase
) : BaseViewModel<SavedViewModel.ViewState, SavedViewModel.Action>(ViewState()) {


    init {
        viewModelScope.launch {
            try {
                val movies = getSavedMoviesUseCase.execute()
                sendAction(Action.SavedMoviesLoadingSuccess(movies))
            } catch (throwable: Throwable) {
                sendAction(Action.SavedMoviesLoadingFailure)
            }
        }
    }

//    val savedMoviesFeed = liveData {
//        emit(Resource.loading(null))
//        try {
//            val movies = getSavedMoviesUseCase.execute()
//            emit(Resource.success(movies))
//        } catch (throwable: Throwable) {
//            emit(Resource.error(throwable.message!!, null))
//        }
//    }

    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.SavedMoviesLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            savedMovies = viewAction.savedMovies
        )

        is Action.SavedMoviesLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            savedMovies = listOf()
        )

        is Action.SavedMoviesLoadingInProgress -> state.copy(
            isLoading = true,
            isError = false,
            savedMovies = viewAction.savedMovies
        )
    }


    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val savedMovies: List<MovieDomainModel> = listOf()
    ) : BaseViewState

    sealed class Action : BaseAction {
        class SavedMoviesLoadingSuccess(val savedMovies: List<MovieDomainModel>) : Action()
        object SavedMoviesLoadingFailure : Action()
        class SavedMoviesLoadingInProgress(val savedMovies: List<MovieDomainModel>) : Action()
    }


}
