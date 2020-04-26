package com.riccardocalligaro.imdbmovies.presentation.feature.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.riccardocalligaro.imdbmovies.R
import com.riccardocalligaro.imdbmovies.databinding.SavedFragmentBinding
import com.riccardocalligaro.imdbmovies.presentation.feature.saved.recyclerview.SavedMovieAdapter
import kotlinx.android.synthetic.main.saved_fragment.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedFragment : Fragment() {


    private val savedViewModel: SavedViewModel by viewModel()

    private val savedMovieAdapter: SavedMovieAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: SavedFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.saved_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewState = savedViewModel.stateLiveData.value

        savedViewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            binding.viewState = it
            savedMovieAdapter.savedMovies = it.savedMovies
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedMoviesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = savedMovieAdapter
        }

    }


}
