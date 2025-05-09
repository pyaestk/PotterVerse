package com.project.potterverse.view.Adapter.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.model.BookDetailsData
import com.project.potterverse.databinding.ItemFavBinding


class FavBookAdapter: RecyclerView.Adapter<FavBookAdapter.FavBookViewHolder>() {
    lateinit var onItemClick: ((BookDetailsData) -> Unit)

    private var bookFavList = ArrayList<BookDetailsData>()
    fun setFavBooks(bookList: ArrayList<BookDetailsData>) {
        this.bookFavList = bookList
        notifyDataSetChanged()
    }
    inner class FavBookViewHolder(val binding: ItemFavBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavBookViewHolder {
        return FavBookViewHolder(ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false ))
    }

    override fun getItemCount(): Int {
        return bookFavList.size
    }

    override fun onBindViewHolder(holder: FavBookViewHolder, position: Int) {
        val currentBook = bookFavList[position]

        Glide.with(holder.itemView)
            .load(currentBook.attributes.cover)
            .into(holder.binding.itemImageView)

        holder.binding.tvItemName.text = currentBook.attributes.title

        holder.itemView.setOnClickListener {
            onItemClick.invoke(bookFavList[position])
        }
    }
}