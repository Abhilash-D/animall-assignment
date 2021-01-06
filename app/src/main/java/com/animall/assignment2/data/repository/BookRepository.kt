package com.animall.assignment2.data.repository

import com.animall.assignment2.data.entities.Book
import com.animall.assignment2.data.local.BookDao
import com.animall.assignment2.data.remote.BookRemoteDataSource
import com.animall.assignment2.util.performGetOperation
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val remoteDataSource: BookRemoteDataSource,
    private val localDataSource: BookDao
) {

    fun getBookDetails(id: String) = performGetOperation(
        databaseQuery = { localDataSource.getBook(id) },
        networkCall = { remoteDataSource.getBookDetails(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getBooks(queryString: String, orderBy: String, projection: String,
                 startIndex: Int, maxResults: Int, apiKey: String) = performGetOperation(
        databaseQuery = { localDataSource.getAllBooks() },
        networkCall = { remoteDataSource.getBooks(queryString, orderBy, projection,
            startIndex, maxResults, apiKey) },
        saveCallResult = { localDataSource.insertAll(it.items!!)

            val totalItems = it.totalItems
            val totalPages = totalItems / maxResults

            var index = startIndex + maxResults
            val remainingItems : MutableList<Book> = mutableListOf()
            for(i in 2..totalPages-1){
                val responseStatus = remoteDataSource.getBooks(queryString, orderBy, projection,
                    index, maxResults, apiKey)
                remainingItems.addAll(responseStatus.data!!.items!!)
                index += maxResults
            }

            localDataSource.insertAll(remainingItems)
        }
    )
}