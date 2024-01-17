package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.data.movies.MovieAttributes
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.data.movies.MovieList
import com.project.potterverse.databinding.ItemMovieBinding

class MovieListsAdapter: RecyclerView.Adapter<MovieListsAdapter.MovieListViewHolder>() {
    lateinit var onItemClick: ((MovieData) -> Unit)
    private var movieList = ArrayList<MovieData>()
    fun setMovies(movieList: ArrayList<MovieData>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    inner class MovieListViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
         return movieList.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        Glide.with(holder.itemView)
             .load(movieList[position].attributes.poster)
             .into(holder.binding.movieImageView)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(movieList[position])
        }

    }

}