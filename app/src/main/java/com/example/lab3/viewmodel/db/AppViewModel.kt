package com.example.lab3.viewmodel.db

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lab3.entity.Category
import com.example.lab3.entity.Food
import com.example.lab3.model.dto.FoodWithCategory
import com.example.lab3.repository.CategoryRepository
import com.example.lab3.repository.FoodRepository

class AppViewModel(
    private val foodRepository: FoodRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val allFoodItems: LiveData<List<FoodWithCategory>> = foodRepository.allFoodItems
    val allCategories: LiveData<List<Category>> = categoryRepository.allCategories

    fun insert(food: Food) = viewModelScope.launch { foodRepository.insert(food) }
    fun update(food: Food) = viewModelScope.launch { foodRepository.update(food) }
    fun delete(food: Food) = viewModelScope.launch { foodRepository.delete(food) }

    class AppViewModelFactory(
        private val foodRepository: FoodRepository,
        private val categoryRepository: CategoryRepository // тут теж твій клас
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppViewModel(foodRepository, categoryRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
