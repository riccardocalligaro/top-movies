package com.riccardocalligaro.imdbmovies.presentation.feature.saved.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.riccardocalligaro.imdbmovies.base.generic.delegate.observer
import com.riccardocalligaro.imdbmovies.databinding.SavedMovieItemBinding
import com.riccardocalligaro.imdbmovies.domain.model.MovieDomainModel

class SavedMovieAdapter : RecyclerView.Adapter<SavedMovieAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null

    var savedMovies: List<MovieDomainModel> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((movie: MovieDomainModel) -> Unit)? = null


    inner class ViewHolder(val binding: SavedMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDomainModel) {
            binding.movie = movie
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = SavedMovieItemBinding.inflate(inflater!!, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = savedMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(savedMovies[position])
    }
}