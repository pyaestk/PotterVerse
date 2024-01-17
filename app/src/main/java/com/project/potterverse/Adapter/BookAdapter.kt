package com.project.potterverse.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.potterverse.data.BookData
import com.project.potterverse.databinding.ItemBookFragmentBinding

class BookAdapter: RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    lateinit var onItemClick: ((BookData) -> Unit)

    private var bookListForHomeFragment = ArrayList<BookData>()
    fun setBooks(bookList: ArrayList<BookData>) {
        this.bookListForHomeFragment = bookList
        notifyDataSetChanged()
    }

    inner class BookViewHolder(val binding: ItemBookFragmentBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(ItemBookFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return bookListForHomeFragment.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(bookListForHomeFragment[position].attributes.cover)
            .into(holder.binding.bookImageView)

        holder.binding.bookTitle.text = bookListForHomeFragment[position].attributes.title
        holder.binding.bookDate.text = bookListForHomeFragment[position].attributes.release_date
        holder.binding.bookAuthor.text = bookListForHomeFragment[position].attributes.author
        holder.binding.bookChapter.text = bookListForHomeFragment[position].relationships.chapters.data.size.toString()

        holder.itemView.setOnClickListener {
            onItemClick.invoke(bookListForHomeFragment[position])
        }
    }
}