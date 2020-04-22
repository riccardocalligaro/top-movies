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


class MovieDetailsFragment : Fragment() {


//    private val movieDetailsViewModel by viewModel()

    private val args: MovieDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: MovieDetailsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false)

        binding.movie = args.movie
        binding.lifecycleOwner = this

        return binding.root
    }

}
