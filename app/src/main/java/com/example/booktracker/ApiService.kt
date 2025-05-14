package com.example.booktracker
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("isbn/{bookId}.json")
    suspend fun getBookById(@Path("bookId") bookId: String): Book
}