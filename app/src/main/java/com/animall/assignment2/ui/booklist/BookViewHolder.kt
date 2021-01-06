package com.animall.assignment2.ui.booklist

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.animall.assignment2.R
import com.animall.assignment2.data.entities.Book
import com.animall.assignment2.databinding.RowItemBookBinding
import com.bumptech.glide.Glide

class BookViewHolder(private val itemBinding: RowItemBookBinding,
                     private val listener: BooksAdapter.BookItemListener)
    : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

    private lateinit var book: Book

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Book) {
        this.book = item
        itemBinding.bookTitle.text = item.title

        if(item.authors.size > 0){
            //show author name in blue
                val authorsList = item.authors.toString()
            itemBinding.bookAuthor.text = authorsList.substring(1, authorsList.length - 1)
            itemBinding.bookAuthor.setTextColor(ContextCompat.getColor(itemBinding.root.context,R.color.blue))
        }else{
            //show pyublisher name in black
            itemBinding.bookAuthor.text = item.publisher
            itemBinding.bookAuthor.setTextColor(ContextCompat.getColor(itemBinding.root.context,R.color.black))
        }

        Glide.with(itemBinding.root)
            .load(item.thumbnail)
            //.transform(CircleCrop())
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedBook(book.id)
    }
}