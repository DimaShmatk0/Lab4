package com.example.lab3

import android.app.Application
import com.example.lab3.db.AppDatabase
import com.example.lab3.repository.CategoryRepository
import com.example.lab3.repository.FoodRepository
import com.example.lab3.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // БД
    private val database by lazy {
        AppDatabase.getDatabase(this, applicationScope)
    }

    // Репозиторії
    val foodRepository by lazy {
        FoodRepository(database.foodDao())
    }

    val categoryRepository by lazy {
        CategoryRepository(database.categoryDao())
    }

    // Додаємо PostRepository
    val postRepository by lazy {
        PostRepository(database.postDao())
    }
}