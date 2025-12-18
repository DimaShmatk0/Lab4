package com.example.lab3.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lab3.api.RetrofitClient
import com.example.lab3.dao.PostDao
import com.example.lab3.entity.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(private val postDao: PostDao) {

    // Отримуємо пости з БД як LiveData
    val allPosts: LiveData<List<Post>> = postDao.getAllPosts().asLiveData()

    // Завантажуємо пости з API та зберігаємо в БД
    suspend fun refreshPosts() {
        withContext(Dispatchers.IO) {
            try {
                val postsFromApi = RetrofitClient.apiService.getPosts()
                postDao.deleteAll() // Спочатку очищаємо старе
                postDao.insertAll(postsFromApi)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    // Очищаємо БД
    suspend fun clearPosts() = withContext(Dispatchers.IO) {
        postDao.deleteAll()
    }

    // Перевіряємо, чи є дані в БД
    suspend fun hasPosts(): Boolean = withContext(Dispatchers.IO) {
        postDao.getCount() > 0
    }
}