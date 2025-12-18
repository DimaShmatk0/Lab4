package com.example.lab3.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab3.MyApplication
import com.example.lab3.entity.Post
import com.example.lab3.viewmodel.PostViewModel

@Composable
fun PostListScreen() {
    // Отримуємо контекст
    val context = LocalContext.current
    val application = context.applicationContext as MyApplication

    // Створюємо ViewModel з Factory
    val viewModel: PostViewModel = viewModel(
        factory = PostViewModel.PostViewModelFactory(application.postRepository)
    )

    // Отримуємо стани з ViewModel
    val posts by viewModel.posts.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Завантажуємо дані при першому відкритті
    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок з кнопками
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Пости з API",
                style = MaterialTheme.typography.headlineSmall
            )

            Row {
                // Кнопка оновлення
                Button(
                    onClick = { viewModel.refreshPosts() },
                    enabled = !isLoading
                ) {
                    Text("Оновити")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Кнопка очищення
                Button(
                    onClick = { viewModel.clearPosts() },
                    enabled = !isLoading && posts.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Text("Очистити")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Повідомлення про помилку
        if (errorMessage != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )

                    IconButton(onClick = { viewModel.clearError() }) {
                        Text("✕")
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Індикатор завантаження
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Column
        }

        // Список постів
        if (posts.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Немає даних",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(posts) { post ->
                    PostCard(post = post)
                }
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "ID: ${post.id} | User ID: ${post.userId}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
}