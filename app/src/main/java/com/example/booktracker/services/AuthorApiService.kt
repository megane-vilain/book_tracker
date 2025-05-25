package com.example.booktracker.services

import com.example.booktracker.models.Author
import com.example.booktracker.models.Work
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthorApiService {
    @GET("{authorId}.json")
    suspend fun getAuthorById(@Path("authorId") authorId: String): Author

    @GET("{workId}.json")
    suspend fun getWorkById(@Path("workId") workId: String): Work
}