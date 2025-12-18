package com.example.lab3.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.lab3.dao.FoodDao
import com.example.lab3.model.dto.FoodWithCategory
import com.example.lab3.entity.Food

class FoodRepository(private val foodDao: FoodDao) {
    val allFoodItems: LiveData<List<FoodWithCategory>> = foodDao.getFoodsWithCategory().asLiveData()

    suspend fun insert(foodItem: Food){
        foodDao.insert(foodItem)
    }

    suspend fun update(foodItem: Food){
        foodDao.update(foodItem)
    }

    suspend fun delete(foodItem: Food){
        foodDao.delete(foodItem)
    }

}