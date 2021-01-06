package com.animall.assignment2.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.animall.assignment2.data.entities.Book
import com.animall.assignment2.util.Resource
import com.animall.assignment2.data.repository.BookRepository

class BookDetailsViewModel @ViewModelInject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _book = _id.switchMap { id ->
        repository.getBookDetails(id)
    }

    val book: LiveData<Resource<Book>> = _book

    fun start(id: String) {
        _id.value = id
    }

}