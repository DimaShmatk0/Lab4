package com.example.lab3.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lab3.model.BannerItem
import com.example.lab3.model.ListItem
import com.example.lab3.model.ProductItem
import com.example.lab3.model.HorizontalList
import com.example.lab3.model.MyHeaderItem


class SearchViewModel : ViewModel() {
    var data = mutableStateListOf<ListItem>()
        private set

    init {
        loadTestData()
    }

    private fun loadTestData() {
        data.addAll(
            listOf(
                // Fruits Section
                MyHeaderItem(1, "Fruits Section"),
                HorizontalList(id =123, values = listOf(
                    "Strawberry",
                    "Blueberry",
                    "Raspberry",
                    "Blackberry",
                    "Cranberry"
                )),
                ProductItem(
                    id = 2,
                    title = "Fruits",
                    price = "$5",
                    attributes = listOf(
                        "Apple",
                        "Banana",
                        "Orange",
                        "Mango",
                        "Pineapple",
                        "Grapes",
                        "Kiwi"
                    )
                ),
                ProductItem(
                    id = 3,
                    title = "Berries",
                    price = "$6",
                    attributes = listOf(
                        "Strawberry",
                        "Blueberry",
                        "Raspberry",
                        "Blackberry",
                        "Cranberry"
                    )
                ),

                // Vegetables Section
                MyHeaderItem(4, "Vegetables Section"),
                ProductItem(
                    id = 5,
                    title = "Vegetables",
                    price = "$3",
                    attributes = listOf(
                        "Carrot",
                        "Tomato",
                        "Cucumber",
                        "Lettuce",
                        "Spinach",
                        "Pepper",
                        "Broccoli"
                    )
                ),
                ProductItem(
                    id = 6,
                    title = "Root Vegetables",
                    price = "$4",
                    attributes = listOf("Potato", "Beet", "Turnip", "Radish", "Onion", "Garlic")
                ),

                // Banner
                BannerItem(id = 7, text = "Super Sale! 50% OFF!"),

                // Snacks Section
                MyHeaderItem(8, "Snacks Section"),
                ProductItem(
                    id = 9,
                    title = "Snacks",
                    price = "$2",
                    attributes = listOf("Chips", "Cookies", "Nuts", "Popcorn", "Crackers", "Pretzels")
                ),
                ProductItem(
                    id = 10,
                    title = "Candies",
                    price = "$1",
                    attributes = listOf("Chocolate", "Gummies", "Lollipops", "Marshmallows", "Caramel")
                ),

                // Drinks Section
                MyHeaderItem(11, "Drinks Section"),
                ProductItem(
                    id = 12,
                    title = "Juices",
                    price = "$3",
                    attributes = listOf(
                        "Orange Juice",
                        "Apple Juice",
                        "Grape Juice",
                        "Mango Juice",
                        "Pineapple Juice"
                    )
                ),
                ProductItem(
                    id = 13,
                    title = "Soft Drinks",
                    price = "$2",
                    attributes = listOf("Cola", "Lemonade", "Root Beer", "Ginger Ale", "Tonic Water")
                )
            )
        )
    }

}