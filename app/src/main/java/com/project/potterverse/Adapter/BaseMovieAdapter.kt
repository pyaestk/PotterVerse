package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.ItemMovieBinding
import com.project.potterverse.databinding.ItemMovieFragmentBinding

class BaseMovieAdapter(var useFragmentBinding: Boolean): RecyclerView.Adapter<BaseMovieAdapter.BaseMovieViewHolder>() {
    lateinit var onItemClick: ((MovieData) -> Unit)

    private var movieList = ArrayList<MovieData>()
    fun setMovies(movieList: ArrayList<MovieData>) {
        this.movieList = movieList
        notifyDataSetChanged()
    }
    inner class BaseMovieViewHolder(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (useFragmentBinding) {
            ItemMovieFragmentBinding.inflate(inflater, parent, false)
        } else {
            ItemMovieBinding.inflate(inflater, parent, false)
        }

        return BaseMovieViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: BaseMovieViewHolder, position: Int) {
        val currentMovie = movieList[position]

        when(val binding = holder.binding) {
            is ItemMovieBinding -> {
                Glide.with(holder.itemView)
                    .load(movieList[position].attributes.poster)
                    .into(binding.movieImageView)
            }
            is ItemMovieFragmentBinding -> {
                Glide.with(holder.itemView)
                    .load(movieList[position].attributes.poster)
                    .into(binding.movieImageView)
                binding.movieTitle.text = movieList[position].attributes.title
                binding.rating.text = movieList[position].attributes.rating
                binding.director.text = movieList[position].attributes.directors[0]
                binding.boxOffice.text = movieList[position].attributes.box_office
                binding.releaseDate.text = movieList[position].attributes.release_date

                binding.buttonSeeMore.setOnClickListener{
                    onItemClick.invoke(movieList[position])
                }
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(movieList[position])
        }

    }
}