package com.example.lab3.model

data class HorizontalList(
    override val id: Long,
    val values: List<String>
) : ListItem
