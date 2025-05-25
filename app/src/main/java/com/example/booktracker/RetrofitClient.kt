package com.example.booktracker

import com.example.booktracker.services.AuthorApiService
import com.example.booktracker.services.IsbnApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://openlibrary.org/"

    val isbnApi: IsbnApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IsbnApiService::class.java)
    }

    val authorApi: AuthorApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthorApiService::class.java)
    }
}