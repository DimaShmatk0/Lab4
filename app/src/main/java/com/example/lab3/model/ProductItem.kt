package com.example.lab3.model

data class ProductItem(
    override val id: Long,
    val title: String,
    val price: String,
    val attributes: List<String>
) : ListItem
