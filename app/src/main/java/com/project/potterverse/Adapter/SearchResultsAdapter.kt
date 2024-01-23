package com.project.potterverse.Adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.project.potterverse.data.BookData
import com.project.potterverse.data.CharactersData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.ItemSearchMovieResultBinding

class SearchResultsAdapter(): RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder>(), Filterable {

    private var itemList = ArrayList<Any>() // Use Any to represent both movies and books
    private val originalSearchResults = ArrayList<Any>()
    init {
        originalSearchResults.addAll(itemList)
    }

    fun setItems(itemList: ArrayList<Any>) {
        this.itemList = itemList as ArrayList<Any>

        originalSearchResults.clear()
        originalSearchResults.addAll(itemList)
        notifyDataSetChanged()
    }
    inner class SearchResultViewHolder(val binding: ItemSearchMovieResultBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchMovieResultBinding.inflate(inflater, parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val currentItem = itemList[position]

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
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Any>()

                if (constraint.isNullOrBlank()){
                    filteredList.addAll(originalSearchResults)
                } else {
                    val filterPattern = constraint.toString().trim { it <= '-'}.toLowerCase()

                    for (item in originalSearchResults) {

                        if (item is BookData) {
                            if (item.attributes.slug.contains(filterPattern)){
                                filteredList.add(item)
                            }
                        }

                        if (item is MovieData) {
                            if (item.attributes.slug.contains(filterPattern)){
                                filteredList.add(item)
                            }
                        }

                    }
                }

                val results = FilterResults()
                results.values = filteredList

                return results

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                itemList = results?.values as ArrayList<Any>
                notifyDataSetChanged()
            }

        }
    }

}