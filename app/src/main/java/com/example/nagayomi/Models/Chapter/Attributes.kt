package com.example.nagayomi.Models.Chapter

import java.io.Serializable

data class Attributes(
    val chapter: String,
    val createdAt: String,
    val externalUrl: String?,
    val pages: Int,
    val publishAt: String,
    val readableAt: String,
    val title: String,
    val translatedLanguage: String,
    val updatedAt: String,
    val version: Int,
    val volume: String?
) : Serializable