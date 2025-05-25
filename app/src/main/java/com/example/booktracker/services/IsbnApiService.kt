package com.example.booktracker.services
import com.example.booktracker.models.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface IsbnApiService {
    @GET("isbn/{bookId}.json")
    suspend fun getBookById(@Path("bookId") bookId: String): Book
}