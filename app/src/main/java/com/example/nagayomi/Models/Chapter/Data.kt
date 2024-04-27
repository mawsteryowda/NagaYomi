package com.example.nagayomi.Models.Chapter

import java.io.Serializable


data class Data(
    val attributes: Attributes,
    val id: String,
    val relationships: List<Relationship>,
    val type: String
) : Serializable