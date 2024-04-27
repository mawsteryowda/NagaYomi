package com.example.nagayomi.Models.Manga

import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("ja-ro")
    val jaRo: String,
    val en: String
)