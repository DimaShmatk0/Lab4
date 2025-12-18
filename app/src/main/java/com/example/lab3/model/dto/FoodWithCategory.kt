package com.example.lab3.model.dto

data class FoodWithCategory(
    val foodId: Long,
    val foodName: String,
    val price: Float,
    val categoryName: String,
    val categoryId: Long
)