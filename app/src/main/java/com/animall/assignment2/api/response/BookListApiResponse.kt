package com.animall.assignment2.api.response

import com.animall.assignment2.data.entities.Book

data class BookListApiResponse (
        val kind : String,
        val items: List<Book>?,
        val totalItems: Int
)