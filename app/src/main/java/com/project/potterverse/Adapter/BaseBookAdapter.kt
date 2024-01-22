package com.project.potterverse.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.project.potterverse.data.BookData
import com.project.potterverse.databinding.ItemBookBinding
import com.project.potterverse.databinding.ItemBookFragmentBinding


class BaseBookAdapter(var useFragmentBinding: Boolean): RecyclerView.Adapter<BaseBookAdapter.BaseBookViewHolder>() {

    lateinit var onItemClick: ((BookData) -> Unit)

    private var bookList = ArrayList<BookData>()

    fun setBooks(bookList: ArrayList<BookData>) {
        this.bookList = bookList
        notifyDataSetChanged()
    }
    inner class BaseBookViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (useFragmentBinding) {
            ItemBookFragmentBinding.inflate(inflater, parent, false)
        } else {
            ItemBookBinding.inflate(inflater, parent, false)
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
            else -> throw IllegalStateException("Unknown binding type")
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(bookList[position])
        }

    }

}
