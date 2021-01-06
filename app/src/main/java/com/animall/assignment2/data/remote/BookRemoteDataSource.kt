package com.animall.assignment2.data.remote

import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val googleBooksService: GoogleBooksService
): BaseDataSource() {

    suspend fun getBooks(queryString: String, orderBy: String, projection: String,
                         startIndex: Int, maxResults: Int, apiKey: String)
            = getResult { googleBooksService.getBooksList(queryString, orderBy, projection,
                            startIndex, maxResults, apiKey) }

    suspend fun getBookDetails(id: String) = getResult { googleBooksService.getBookDetails(id) }
}