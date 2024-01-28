package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.data.movies.MovieList
import com.project.potterverse.databinding.ItemMovieBinding
import com.project.potterverse.databinding.ItemMovieFragmentBinding
import com.project.potterverse.databinding.ItemSearchMovieResultBinding
import java.lang.IllegalArgumentException

class BaseMovieAdapter(var useFragmentBinding: Int): RecyclerView.Adapter<BaseMovieAdapter.BaseMovieViewHolder>(), Filterable {
    lateinit var onItemClick: ((MovieData) -> Unit)

    private var movieList = ArrayList<MovieData>()

    private var originalMovieLists = ArrayList<MovieData>()

    init {
        // Initialize originalBookLists with the initial data
        originalMovieLists.addAll(movieList)
    }
    fun setMovies(movieList: ArrayList<MovieData>) {
        this.movieList = movieList

        originalMovieLists.clear()
        originalMovieLists.addAll(movieList)
        notifyDataSetChanged()
    }
    inner class BaseMovieViewHolder(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = when(useFragmentBinding) {
            1 -> ItemMovieFragmentBinding.inflate(inflater, parent, false)
            2 -> ItemMovieBinding.inflate(inflater, parent, false)
            3 -> ItemSearchMovieResultBinding.inflate(inflater, parent, false)
            else -> throw IllegalArgumentException("error")
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
                binding.director.text = movieList[position].attributes.directors!![0]
                binding.boxOffice.text = movieList[position].attributes.box_office
                binding.releaseDate.text = movieList[position].attributes.release_date

                binding.buttonSeeMore.setOnClickListener{
                    onItemClick.invoke(movieList[position])
                }


            }
            is ItemSearchMovieResultBinding -> {
                Glide.with(holder.itemView)
                    .load(movieList[position].attributes.poster)
                    .into(binding.itemImage)
                binding.itemTitle.text = currentMovie.attributes.title
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(movieList[position])
        }

    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<MovieData>()

                if (constraint.isNullOrBlank()){
                    filteredList.addAll(originalMovieLists)
                } else {
                    val filterPattern = constraint.toString().trim { it <= '-'}.toLowerCase()

                    for (movie in originalMovieLists) {
                        if (movie.attributes.slug!!.contains(filterPattern)) {
                            filteredList.add(movie)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movieList = results?.values as ArrayList<MovieData>
                notifyDataSetChanged()
            }

        }
    }
}