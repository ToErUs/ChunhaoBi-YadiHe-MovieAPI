package com.example.movieapi.api

import com.example.movieapi.MovieInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbService {
    @GET("/")
    fun getMovieInfo(
        @Query("apikey") apiKey: String,
        @Query("t") movieTitle: String
    ): Call<MovieInfo>
}