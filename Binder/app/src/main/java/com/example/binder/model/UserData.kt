package com.example.binder.model

import com.squareup.moshi.Json

data class UserData (
    @field:Json(name = "userId") val userId: Long,
    @field:Json(name = "year") val year: Int,
    @field:Json(name = "contacts") val personal: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "photo") val photo: String?,
)
