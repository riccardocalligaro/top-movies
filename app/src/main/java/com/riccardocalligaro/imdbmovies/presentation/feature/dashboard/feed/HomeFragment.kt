package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.riccardocalligaro.imdbmovies.R
import com.riccardocalligaro.imdbmovies.databinding.HomeFragmentBinding
import com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed.recyclerview.FeedAdapter
import com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed.recyclerview.MoviesAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: HomeFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        binding.lifecycleOwner = this
        binding.viewState = homeViewModel.stateLiveData.value

        homeViewModel.stateLiveData.observe(viewLifecycleOwner, {
            binding.viewState = it

            if (it.feedItems.isNotEmpty()) {
                Timber.i("Success")
                val list = it.feedItems
                val adapter = FeedAdapter(
                    list,
                    { movies ->
                        MoviesAdapter(movies) { position ->
                            val movie = movies[position]
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                                    movie
                                )

                            findNavController().navigate(action)
                        }.apply {
                            setHasStableIds(true)
                        }
                    },

                    { position ->
                        Timber.i("Feed item clicked $position")
                    }
                ).apply {
                    setHasStableIds(true)
                }
                binding.feedRecyclerView.adapter = adapter
            }
        })

        return binding.root
    }


}
