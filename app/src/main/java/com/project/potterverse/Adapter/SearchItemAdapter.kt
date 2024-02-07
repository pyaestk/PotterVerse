package com.project.potterverse.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.CharacterDetails.CharacterDetailsData
import com.project.potterverse.data.bookDetails.BookDetailsData
import com.project.potterverse.data.movieDetails.MovieDetailData
import com.project.potterverse.databinding.ItemSearchMovieResultBinding
import java.lang.IllegalArgumentException


sealed class SearchItem {
    data class Book(val data: BookDetailsData) : SearchItem()
    data class Movie(val data: MovieDetailData) : SearchItem()
    data class Character(val data: CharacterDetailsData) : SearchItem()
}
class SearchItemAdapter : RecyclerView.Adapter<SearchItemAdapter.SearchResultViewHolder>() {

    lateinit var onItemClick: ((SearchItem) -> Unit)

    private val diffCallback = object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.javaClass == newItem.javaClass && getItemId(oldItem) == getItemId(newItem)
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setItems(newItemList: List<SearchItem>) {
        differ.submitList(newItemList)
    }

    fun clearItem(){
        differ.currentList.clear()
    }

    inner class SearchResultViewHolder(private val binding: ItemSearchMovieResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchItem) {
            when (item) {
                is SearchItem.Book -> {
                    Glide.with(itemView)
                        .load(item.data.attributes.cover)
                        .into(binding.itemImage)
                    binding.itemTitle.text = item.data.attributes.title
                    binding.itemType.text = "Book"
                }
                is SearchItem.Movie -> {

                    val resourceId = when(item.data.attributes.title) {
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
                        binding.itemImage.setImageResource(resourceId)
                    }
                    binding.itemTitle.text = item.data.attributes.title
                    binding.itemType.text = "Movie"
                }
                is SearchItem.Character -> {
                    if (item.data.attributes.image.isNullOrEmpty()) {
                        binding.itemImage.setImageResource(R.drawable.witchhat)
                    } else {
                        Glide.with(itemView)
                            .load(item.data.attributes.image)
                            .into(binding.itemImage)
                    }
                    binding.itemTitle.text = item.data.attributes.name
                    binding.itemType.text = "Character"
                }
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchMovieResultBinding.inflate(inflater, parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.itemView.setOnClickListener {
            onItemClick.invoke(currentItem)
        }
        holder.bind(currentItem)
    }

//    private fun getItemId(item: SearchItem): Long {
//        return when (item) {
//            is SearchItem.Book -> item.data.id.hashCode().toLong()
//            is SearchItem.Movie -> item.data.id.hashCode().toLong()
//            is SearchItem.Character -> item.data.id.hashCode().toLong()
//        }
//    }

    private fun getItemId(item: SearchItem): String {
        return when (item) {
            is SearchItem.Book -> item.data.id
            is SearchItem.Movie -> item.data.id
            is SearchItem.Character -> item.data.id
        }
    }
}