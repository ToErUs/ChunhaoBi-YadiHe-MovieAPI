package com.example.movieapi

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class MovieInfo(
    @Json(name = "Title")@SerializedName("Title") val title: String,
    @Json(name = "Year")@SerializedName("Year") val year: String,
    @Json(name = "Poster")@SerializedName("Poster") val poster: String,
    // Add other fields as needed
)
