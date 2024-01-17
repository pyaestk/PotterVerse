package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.data.BookData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.FragmentHomeBinding
import com.project.potterverse.databinding.ItemBookBinding

class BookListAdapter: RecyclerView.Adapter<BookListAdapter.BookListViewHolder>() {
    lateinit var onItemClick: ((BookData) -> Unit)
    private var bookList = ArrayList<BookData>()
    fun setBooks(bookList: ArrayList<BookData>) {
        this.bookList = bookList
        notifyDataSetChanged()
    }

    inner class BookListViewHolder(val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        return BookListViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(bookList[position].attributes.cover)
            .into(holder.binding.bookImageView)

        holder.binding.bookTitle.text = bookList[position].attributes.title
        holder.binding.bookDate.text = bookList[position].attributes.release_date
        holder.binding.bookAuthor.text = bookList[position].attributes.author
        holder.binding.bookChapter.text = bookList[position].relationships.chapters.data.size.toString()
        holder.itemView.setOnClickListener {
            onItemClick.invoke(bookList[position])
        }
    }
}