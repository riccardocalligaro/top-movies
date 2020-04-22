package com.riccardocalligaro.imdbmovies.presentation.feature.dashboard.feed.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riccardocalligaro.imdbmovies.databinding.MovieItemBinding
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel

class MoviesAdapter(
    private val movies: List<MovieDomainModel>,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = MovieItemBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return movies[position].id
    }

    override fun getItemCount(): Int = movies.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.binding.movie = movie
    }

    inner class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}