package com.example.lab3.dao

import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lab3.entity.Food
import com.example.lab3.model.dto.FoodWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("""
        SELECT f.id AS foodId,
               f.name AS foodName,
               f.price,
               c.id AS categoryId,
               c.name AS categoryName
        FROM foods f
        JOIN categories c ON f.categoryId = c.id
    """)
    fun getFoodsWithCategory(): Flow<List<FoodWithCategory>>

    @Insert
    suspend fun insert(food: Food)

    @Update
    suspend fun update(food: Food)

    @Delete
    suspend fun delete(food: Food)


}
