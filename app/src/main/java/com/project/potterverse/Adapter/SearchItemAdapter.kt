package com.project.potterverse.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.R
import com.project.potterverse.data.BookData
import com.project.potterverse.data.CharactersData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.ItemSearchMovieResultBinding
import java.lang.IllegalArgumentException

class SearchItemAdapter : RecyclerView.Adapter<SearchItemAdapter.SearchResultViewHolder>() {

    // Define your callback for DiffUtil
    private val diffCallback = object : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem.javaClass == newItem.javaClass && getItemId(oldItem) == getItemId(newItem)
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is BookData && newItem is BookData -> oldItem == newItem
                oldItem is MovieData && newItem is MovieData -> oldItem == newItem
                oldItem is CharactersData && newItem is CharactersData -> oldItem == newItem
                else -> false
            }
        }
    }

    // Initialize AsyncListDiffer
    private val differ = AsyncListDiffer(this, diffCallback)

    // Set new items using AsyncListDiffer
    fun setItems(newItemList: List<Any>) {
        differ.submitList(newItemList)
    }

    inner class SearchResultViewHolder(val binding: ItemSearchMovieResultBinding) :
        RecyclerView.ViewHolder(binding.root)

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

        if (currentItem is BookData) {
            Glide.with(holder.itemView)
                .load(currentItem.attributes.cover)
                .into(holder.binding.itemImage)
            holder.binding.itemTitle.text = currentItem.attributes.title
        }

        if (currentItem is MovieData) {
            Glide.with(holder.itemView)
                .load(currentItem.attributes.poster)
                .into(holder.binding.itemImage)
            holder.binding.itemTitle.text = currentItem.attributes.title
        }

        // Example:
        if (currentItem is CharactersData) {
            if (currentItem.attributes.image.isNullOrEmpty()) {
                holder.binding.itemImage.setImageResource(R.drawable.witchhat)
            } else {
                Glide.with(holder.itemView)
                    .load(currentItem.attributes.image)
                    .into(holder.binding.itemImage)
            }
            holder.binding.itemTitle.text = currentItem.attributes.name
        }
    }

    private fun getItemId(item: Any): Long {
        return when (item) {
            is BookData -> item.id.hashCode().toLong()
            is MovieData -> item.id.hashCode().toLong()
            is CharactersData -> item.id.hashCode().toLong()
            else -> throw IllegalArgumentException("Unsupported item type")
        }
    }
}