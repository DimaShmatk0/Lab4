package com.example.lab3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import com.example.lab3.entity.Post
import com.example.lab3.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostViewModel(private val postRepository: PostRepository) : ViewModel() {

    // Отримуємо пости з БД
    val posts: LiveData<List<Post>> = postRepository.allPosts

    // Стан для завантаження
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Стан для помилок
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Завантажуємо дані з API
    fun loadPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Перевіряємо, чи є дані в БД
                val hasData = postRepository.hasPosts()

                if (!hasData) {
                    // Якщо немає даних, завантажуємо з API
                    postRepository.refreshPosts()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Помилка завантаження: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Оновлюємо дані з API
    fun refreshPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                postRepository.refreshPosts()
            } catch (e: Exception) {
                _errorMessage.value = "Помилка оновлення: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Очищаємо БД
    fun clearPosts() {
        viewModelScope.launch {
            try {
                postRepository.clearPosts()
            } catch (e: Exception) {
                _errorMessage.value = "Помилка очищення: ${e.message}"
            }
        }
    }

    // Очищаємо помилку
    fun clearError() {
        _errorMessage.value = null
    }

    // Factory для створення ViewModel
    class PostViewModelFactory(
        private val postRepository: PostRepository
    ) : androidx.lifecycle.ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
                return PostViewModel(postRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}