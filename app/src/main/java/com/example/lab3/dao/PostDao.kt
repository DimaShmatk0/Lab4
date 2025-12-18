package com.example.lab3.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab3.entity.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    // Отримуємо всі пости з БД
    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun getAllPosts(): Flow<List<Post>>

    // Вставляємо список постів
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    // Видаляємо всі пости
    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    // Отримуємо кількість постів
    @Query("SELECT COUNT(*) FROM posts")
    suspend fun getCount(): Int
}