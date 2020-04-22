package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riccardocalligaro.imdbmovies.databinding.FeedItemBinding
import com.riccardocalligaro.imdbmovies.domain.model.FeedItemDomainModel
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel


class FeedAdapter(
    private val feedItems: List<FeedItemDomainModel>,
    private val getMoviesAdapter: (List<MovieDomainModel>) -> MoviesAdapter,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = FeedItemBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = feedItems.size

    override fun getItemId(position: Int): Long {
        return feedItems[position].id
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed = feedItems[position]
        holder.binding.feedItem = feed
        val adapter = getMoviesAdapter(feed.movies)
        holder.binding.feedMoviesRecyclerView.adapter = adapter
    }

    inner class ViewHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}