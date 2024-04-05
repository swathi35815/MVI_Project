package com.example.mviproject.api

import com.example.mviproject.model.ImageData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface ApiImageInterface{
    @GET("photos")
    fun getData(): Call<ArrayList<ImageData>>
}

class ApiImageClient {
    companion object{
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiImageInterface::class.java)
    }
}