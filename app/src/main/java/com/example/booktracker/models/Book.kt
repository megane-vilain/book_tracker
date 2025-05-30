package com.example.booktracker.models

import com.google.gson.annotations.SerializedName

data class Book (
    val title: String,
    @SerializedName("number_of_pages")
    val nbPages: Int,
    val works: ArrayList<Work>,
    val authors: List<Author>
)