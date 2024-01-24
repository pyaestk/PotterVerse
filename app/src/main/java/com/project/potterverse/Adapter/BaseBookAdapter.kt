package com.project.potterverse.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.project.potterverse.data.BookData
import com.project.potterverse.data.movies.MovieData
import com.project.potterverse.databinding.ItemBookBinding
import com.project.potterverse.databinding.ItemBookFragmentBinding
import com.project.potterverse.databinding.ItemMovieFragmentBinding
import com.project.potterverse.databinding.ItemSearchMovieResultBinding


class BaseBookAdapter(var useFragmentBinding: Int): RecyclerView.Adapter<BaseBookAdapter.BaseBookViewHolder>() {

    lateinit var onItemClick: ((BookData) -> Unit)

    private var bookList = ArrayList<BookData>()

    private var originalBookLists = ArrayList<BookData>()
    init {
        // Initialize originalBookLists with the initial data
        originalBookLists.addAll(bookList)
    }

    fun setBooks(bookList: ArrayList<BookData>) {
        this.bookList = bookList

        originalBookLists.clear()
        originalBookLists.addAll(bookList)
        notifyDataSetChanged()
    }
    inner class BaseBookViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when(useFragmentBinding) {
            1 -> ItemBookFragmentBinding.inflate(inflater, parent, false)
            2 -> ItemBookBinding.inflate(inflater, parent, false)
            3 -> ItemSearchMovieResultBinding.inflate(inflater, parent, false)
            else -> throw IllegalArgumentException("error")
        }
        return BaseBookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BaseBookViewHolder, position: Int) {
        val currentBook = bookList[position]

        when(val binding = holder.binding){
            is ItemBookBinding -> {
                Glide.with(holder.itemView)
                    .load(bookList[position].attributes.cover)
                    .into(binding.bookImageView)
                binding.bookTitle.text = currentBook.attributes.title
                binding.bookDate.text = currentBook.attributes.release_date
                binding.bookAuthor.text = currentBook.attributes.author
                binding.bookChapter.text = currentBook.relationships.chapters.data.size.toString()
            }
            is ItemBookFragmentBinding -> {
                Glide.with(holder.itemView)
                    .load(bookList[position].attributes.cover)
                    .into(binding.bookImageView)
                binding.bookTitle.text = currentBook.attributes.title
                binding.bookDate.text = currentBook.attributes.release_date
                binding.bookAuthor.text = currentBook.attributes.author
                binding.bookChapter.text = currentBook.relationships.chapters.data.size.toString()
            }
            is ItemSearchMovieResultBinding -> {
                Glide.with(holder.itemView)
                    .load(bookList[position].attributes.cover)
                    .into(binding.itemImage)
                binding.itemTitle.text = currentBook.attributes.title
            }
            else -> throw IllegalStateException("Unknown binding type")
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(bookList[position])
        }

    }

}
