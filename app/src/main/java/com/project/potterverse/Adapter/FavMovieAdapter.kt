package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.ItemFavBinding

class FavMovieAdapter: RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>() {
    lateinit var onItemClick: ((MovieDetailData) -> Unit)

    private var movieFavList = ArrayList<MovieDetailData>()
    fun setFavMovies(movieList: ArrayList<MovieDetailData>) {
        this.movieFavList = movieList
        notifyDataSetChanged()
    }
    inner class FavMovieViewHolder(val binding: ItemFavBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieViewHolder {
        return FavMovieViewHolder(ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount(): Int {
        return movieFavList.size
    }

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        val currentMovie = movieFavList[position]
        val binding = holder.binding

        setMovieImageResource(binding.itemImageView, currentMovie.attributes.title)
        
        holder.itemView.setOnClickListener {
            onItemClick.invoke(movieFavList[position])
        }
    }

    private fun setMovieImageResource(itemImageView: ImageView, title: String?) {
        title?.let {
            val resourceId = when(it) {
                "Harry Potter and the Philosopher's Stone" -> R.drawable.h1
                "Harry Potter and the Chamber of Secrets" -> R.drawable.h2
                "Harry Potter and the Prisoner of Azkaban" -> R.drawable.h3
                "Harry Potter and the Goblet of Fire" -> R.drawable.h4
                "Harry Potter and the Order of the Phoenix" -> R.drawable.h5
                "Harry Potter and the Half-Blood Prince" -> R.drawable.h6
                "Harry Potter and the Deathly Hallows - Part 1" -> R.drawable.h7
                "Harry Potter and the Deathly Hallows – Part 2" -> R.drawable.h8
                "Fantastic Beasts and Where to Find Them" -> R.drawable.f1
                "Fantastic Beasts: The Crimes of Grindelwald" -> R.drawable.f2
                "Fantastic Beasts: The Secrets of Dumbledore" -> R.drawable.f3
                else -> 0
            }
            if (resourceId != 0) {
                itemImageView.setImageResource(resourceId)
            }
        }
    }
}