package com.animall.assignment2.data.remote

import com.animall.assignment2.api.response.BookListApiResponse
import com.animall.assignment2.data.entities.Book
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksService {
    @GET(".")
    suspend fun getBooksList(@Query("q") queryString: String,
                     @Query("orderBy") orderBy: String,
                     @Query("projection") projection: String,
                     @Query("startIndex") startIndex : Int,
                     @Query("maxResults") maxResults : Int,
                     @Query("key") apiKey: String): Response<BookListApiResponse>

    @GET("{id}")
    suspend fun getBookDetails(@Path("id") id: String): Response<Book>
}