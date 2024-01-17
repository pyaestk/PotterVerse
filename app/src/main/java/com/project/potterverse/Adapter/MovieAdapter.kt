package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.ItemMovieFragmentBinding

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    lateinit var onItemClick: ((MovieData) -> Unit)

    private var movieList = ArrayList<MovieData>()
    fun setMovies(movieList: ArrayList<MovieData>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }
    inner class MovieViewHolder(val binding: ItemMovieFragmentBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(movieList[position].attributes.poster)
            .into(holder.binding.movieImageView)
        holder.binding.movieTitle.text = movieList[position].attributes.title
        holder.binding.rating.text = movieList[position].attributes.rating
        holder.binding.director.text = movieList[position].attributes.directors[0]
        holder.binding.boxOffice.text = movieList[position].attributes.box_office
        holder.binding.releaseDate.text = movieList[position].attributes.release_date

        holder.itemView.setOnClickListener {
            onItemClick.invoke(movieList[position])
        }
    }
}