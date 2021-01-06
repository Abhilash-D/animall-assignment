package com.animall.assignment2.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.animall.assignment2.AnimallApplication
import com.animall.assignment2.R
import com.animall.assignment2.data.entities.Book
import com.animall.assignment2.data.repository.BookRepository
import com.animall.assignment2.util.Constants
import com.animall.assignment2.util.Resource

class BookListViewModel @ViewModelInject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private var startIndex : Int = 0

    val books = repository.getBooks(Constants.GoogleBooksApiKeywords.SUBJECT+":"+ SUBJECT_AGRICULTURE,
        Constants.GoogleBooksApiKeywords.ORDER_BY_RELEVANCE,
        Constants.GoogleBooksApiKeywords.PROJECTION_LITE, startIndex, MAX_RESULTS,
        AnimallApplication.applicationContext().resources.getString(R.string.google_books_api_key))

    companion object{
        const val SUBJECT_AGRICULTURE = "agriculture"
        const val MAX_RESULTS = 40
    }
}