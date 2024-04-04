package com.example.binder.model

import com.squareup.moshi.Json

data class Book(
    @field:Json(name = "id") val id: Long?,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "author") val author: String,
    @field:Json(name = "year") val year: Int?,
    @field:Json(name = "description") val description: String
)

fun Book.toInfoStr(): String = if (year != null) "$author/$title ($year)" else "$author/$title"