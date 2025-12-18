package com.example.lab3.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private  val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl( "https://jsonplaceholder.typicode.com/" )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: ApiService = retrofit.create(ApiService:: class .java)
}