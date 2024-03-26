package com.example.movieapi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.launch
import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.example.movieapi.api.OMDbService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var editTextMovieName: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var imageViewPoster: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        editTextMovieName = findViewById(R.id.edit_text)
        buttonSubmit = findViewById(R.id.submit_button)
        imageViewPoster = findViewById(R.id.imageViewPoster)
        buttonSubmit.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val movieName = editTextMovieName.text.toString()
                val movieInfo=fetchMovieInfo(movieName)
                withContext(Dispatchers.Main) {
                    Picasso.get().load(movieInfo.poster).into(imageViewPoster)
                }
            }
        }

    }

    private suspend fun fetchMovieInfo(name:String): MovieInfo {
        var ret:MovieInfo= MovieInfo("","","")
        withContext(Dispatchers.IO) {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(OMDbService::class.java)

            val apiKey = "81a2d511"
            val call = service.getMovieInfo(apiKey, name)

            val response = call.execute()


            if (response.isSuccessful) {
                val movieInfo = response.body()
                ret= response.body()!!
                println("Movie Title: ${movieInfo?.title}")
                println("Year: ${movieInfo?.year}")

            } else {
                println("Failed to get movie information: ${response.errorBody()}")
            }



        }
        return ret
    }
}

