package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.ItemMovieBinding
import com.project.potterverse.databinding.ItemMovieFragmentBinding
import com.project.potterverse.databinding.ItemSearchMovieResultBinding
import java.lang.IllegalArgumentException

class BaseMovieAdapter(var useFragmentBinding: Int): RecyclerView.Adapter<BaseMovieAdapter.BaseMovieViewHolder>(), Filterable {
    lateinit var onItemClick: ((MovieDetailData) -> Unit)

    private var movieList = ArrayList<MovieDetailData>()

    private var originalMovieLists = ArrayList<MovieDetailData>()

    init {
        // Initialize originalBookLists with the initial data
        originalMovieLists.addAll(movieList)
    }
    fun setMovies(movieList: ArrayList<MovieDetailData>) {
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
            else -> throw IllegalArgumentException("error")
        }

        return BaseMovieViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: BaseMovieViewHolder, position: Int) {
        val currentMovie = movieList[position]
        val binding = holder.binding

        when(binding) {
            is ItemMovieBinding -> setMovieImageResource(binding.itemImageView, currentMovie.attributes.title)
            is ItemMovieFragmentBinding -> {
                setMovieImageResource(binding.itemImageView, currentMovie.attributes.title)
                binding.movieTitle.text = currentMovie.attributes.title
                binding.rating.text = currentMovie.attributes.rating
                binding.director.text = currentMovie.attributes.directors?.getOrNull(0) ?: ""
                binding.boxOffice.text = currentMovie.attributes.box_office
                binding.releaseDate.text = currentMovie.attributes.release_date
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick.invoke(movieList[position])
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


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<MovieDetailData>()

                if (constraint.isNullOrBlank()){
                    filteredList.addAll(originalMovieLists)
                } else {
                    val filterPattern = constraint.toString().trim { it <= '-'}.lowercase()

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
                movieList = results?.values as ArrayList<MovieDetailData>
                notifyDataSetChanged()
            }

        }
    }
}