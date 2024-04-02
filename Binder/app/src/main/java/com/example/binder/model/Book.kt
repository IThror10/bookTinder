package com.example.binder.model

class Book(
    val name: String,
    val author: String,
    val year: Int
)

fun Book.toInfoStr(): String = "$author/$name ($year)"