package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.riccardocalligaro.imdbmovies.R
import com.riccardocalligaro.imdbmovies.databinding.MovieDetailsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MovieDetailsFragment : Fragment() {


    private val movieDetailsViewModel: MovieDetailsViewModel by viewModel()

    private val args: MovieDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: MovieDetailsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false)

        movieDetailsViewModel.init(args.movie)


        Timber.i("Movie saved: ${args.movie.saved}")


        binding.movie = args.movie
        binding.viewModel = movieDetailsViewModel
        binding.lifecycleOwner = this




        return binding.root
    }


}
