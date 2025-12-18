package com.example.lab3.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab3.viewmodel.CounterViewModel

@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    val count by viewModel.counter.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)) {
        Text(text = "Counter $count")
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            Button(onClick = { viewModel.increment() }) { Text("+") }
            Button(onClick = { viewModel.decrement() }) { Text("-") }
        }
    }
}