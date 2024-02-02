package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.ItemMovieFavBinding

class FavMovieAdapter: RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>() {
    lateinit var onItemClick: ((MovieDetailData) -> Unit)

    private var movieFavList = ArrayList<MovieDetailData>()
    fun setFavMovies(movieList: ArrayList<MovieDetailData>) {
        this.movieFavList = movieList
        notifyDataSetChanged()
    }
    inner class FavMovieViewHolder(val binding: ItemMovieFavBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieViewHolder {
        return FavMovieViewHolder(ItemMovieFavBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount(): Int {
        return movieFavList.size
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        val currentMovie = movieFavList[position]

        Glide.with(holder.itemView)
            .load(currentMovie.attributes.poster)
            .into(holder.binding.movieImageView)


        holder.itemView.setOnClickListener {
            onItemClick.invoke(movieFavList[position])
        }
    }
}