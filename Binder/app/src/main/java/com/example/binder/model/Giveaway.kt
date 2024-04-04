package com.example.binder.model

import com.squareup.moshi.Json

data class Giveaway(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "userId") val userId: Long,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "book") val book: Book,
    @field:Json(name = "photo") val photo: String?,
)