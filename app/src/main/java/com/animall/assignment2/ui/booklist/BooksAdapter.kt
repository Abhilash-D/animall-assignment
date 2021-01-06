package com.animall.assignment2.ui.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.animall.assignment2.data.entities.Book
import com.animall.assignment2.databinding.RowItemBookBinding

class BooksAdapter(private val listener: BookItemListener)
    : RecyclerView.Adapter<BookViewHolder>() {

    interface BookItemListener {
        fun onClickedBook(bookId: String)
    }

    private val items = ArrayList<Book>()

    fun setItems(items: ArrayList<Book>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding: RowItemBookBinding = RowItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int)
            = holder.bind(items[position])
}

