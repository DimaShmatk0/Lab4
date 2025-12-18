package com.example.lab3.model

data class MyHeaderItem(
    override val id: Long,
    val title: String
) : ListItem
