package com.example.lab3.ui.screen.mail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MailMainScreen(onNavigateToCompose: () -> Unit) {
    var recipientEmail by rememberSaveable { mutableStateOf("") }
    var emailSubject by rememberSaveable { mutableStateOf("") }
    var emailBody by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(20.dp)) {
        OutlinedTextField(
            value = recipientEmail,
            onValueChange = { recipientEmail = it },
            label = { Text("Кому") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        OutlinedTextField(
            value = emailSubject,
            onValueChange = { emailSubject = it },
            label = { Text("Тема") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        OutlinedTextField(
            value = emailBody,
            onValueChange = { emailBody = it },
            label = { Text("Введіть повідомлення") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Button(
            onClick = { onNavigateToCompose() },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Перейти на другий підекран")
        }
    }
}
