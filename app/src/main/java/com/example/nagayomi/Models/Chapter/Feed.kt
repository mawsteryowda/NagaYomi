package com.example.nagayomi.Models.Chapter

data class Feed(
    val data: List<Data>,
    val limit: Int,
    val offset: Int,
    val response: String,
    val result: String,
    val total: Int
)