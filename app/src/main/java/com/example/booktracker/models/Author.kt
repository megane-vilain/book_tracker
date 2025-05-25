package com.example.booktracker.models

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("personal_name")
    val name: String,
    val key: String
)
