package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel
import com.riccardocalligaro.imdbmovies.domain.usecase.DiscardMovieUseCase
import com.riccardocalligaro.imdbmovies.domain.usecase.SaveMovieUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailsViewModel(
    private val saveMovieUseCase: SaveMovieUseCase,
    private val discardMovieUseCase: DiscardMovieUseCase
) : ViewModel() {

    var movie: MovieDomainModel? = null


    var saved = MutableLiveData<Boolean>(false)

    fun init(movie: MovieDomainModel) {
        this.movie = movie
        saved.value = movie.saved
    }

    fun saveMovie() {
        if (saved.value!!) {
            saved.value = false
            // un-save movie
            Timber.i("unsaved")
            viewModelScope.launch {
                discardMovieUseCase.execute(movie!!)
            }
        } else {
            saved.value = true
            // save movie
            viewModelScope.launch {
                saveMovieUseCase.execute(movie!!)
            }
        }
    }
}
