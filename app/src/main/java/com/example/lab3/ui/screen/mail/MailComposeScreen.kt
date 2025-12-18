package com.example.lab3.ui.screen.mail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MailComposeScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.padding(40.dp)) {
        Text("Сторінка складання листа", modifier = Modifier.padding(bottom = 12.dp))
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Коротка нотатка") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        Row {
            Button(onClick = onBack) {
                Text("Назад")
            }
        }
    }
}