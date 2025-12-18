package com.example.lab3.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lab3.dao.CategoryDao
import com.example.lab3.entity.Category

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories().asLiveData()

    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    suspend fun update(category: Category) {
        categoryDao.update(category)
    }

    suspend fun delete(category: Category){
        categoryDao.delete(category)
    }
}