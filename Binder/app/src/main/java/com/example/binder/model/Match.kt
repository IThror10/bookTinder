package com.example.binder.model

import com.squareup.moshi.Json

data class Match (
    @field:Json(name = "ours") val ours: Giveaway,
    @field:Json(name = "other") val other: Giveaway
)