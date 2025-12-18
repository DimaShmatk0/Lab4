package com.example.lab3.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Додаємо анотацію @Entity для Room
@Entity(tableName = "posts")
data class Post(
    @PrimaryKey // id вже є у API, тому autoGenerate = false
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)