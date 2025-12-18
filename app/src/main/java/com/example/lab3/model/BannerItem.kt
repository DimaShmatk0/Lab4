package com.example.lab3.model

data class BannerItem(
    override val id: Long,
    val text: String
) : ListItem
