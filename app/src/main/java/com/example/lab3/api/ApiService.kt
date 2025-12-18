package com.example.lab3.api

import com.example.lab3.entity.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}