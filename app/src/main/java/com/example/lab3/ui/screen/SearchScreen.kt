package com.example.lab3.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab3.model.BannerItem
import com.example.lab3.model.HorizontalList
import com.example.lab3.model.MyHeaderItem
import com.example.lab3.model.ProductItem
import com.example.lab3.viewmodel.SearchViewModel


@Composable
fun SearchScreen(viewModel: SearchViewModel = viewModel()) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = viewModel.data,
            key = { item ->
                when (item) {
                    is ProductItem -> item.id
                    is MyHeaderItem -> item.id
                    is HorizontalList -> item.id
                    is BannerItem -> item.id
                    else -> {}
                }
            }
        ) { item ->
            when (item) {
                is MyHeaderItem -> HeaderItemView(item)
                is HorizontalList -> HorizontalList(item)
                is ProductItem -> ProductItemView(item)
                is BannerItem -> BannerItemView(item)
                else -> {}
            }
        }
    }
}

@Composable
fun ProductItemView(item: ProductItem) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth(),
        color = Color(0xFFC8E6C9),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Price: ${item.price}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(item.attributes) { price ->
                    Text(
                        text = price,
                        modifier = Modifier
                            .background(Color(0xFF81C784), RoundedCornerShape(6.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalList(item: HorizontalList){
    Surface(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth(),
        color = Color(0xFFC8E6C9),
        shape = RoundedCornerShape(8.dp)
    ) {LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(item.values) { value ->
            Text(
                text = value,
                modifier = Modifier
                    .background(Color(0xFF81C784), RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }}
}

@Composable
fun HeaderItemView(item: MyHeaderItem) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth(),
        color = Color(0xFF90CAF9),
        shape = RoundedCornerShape(16.dp)

    ) {
        Text(
            text = item.title,
            modifier = Modifier.padding(12.dp),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BannerItemView(item: BannerItem) {
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = Color(0xFFFFEB3B),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = item.text,
            modifier = Modifier.padding(16.dp),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
