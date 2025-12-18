package com.example.lab3.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lab3.dao.CategoryDao
import com.example.lab3.dao.FoodDao
import com.example.lab3.dao.PostDao
import com.example.lab3.entity.Category
import com.example.lab3.entity.Food
import com.example.lab3.entity.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(
    entities = [
        Food::class,
        com.example.lab3.entity.Category::class,
        Post::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun categoryDao(): CategoryDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "food_item_database"
                ).addCallback(FoodItemCallback(scope)).build()

                INSTANCE = instance
                return instance
            }
        }

        private class FoodItemCallback(val scope: CoroutineScope) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                scope.launch(Dispatchers.IO) {
                    try {
                        INSTANCE?.let { database ->
                            val fruitId = database.categoryDao().insert(Category(name = "Fruits"))
                            val vegetableId = database.categoryDao().insert(Category(name = "Vegetables"))

                            val categories = database.categoryDao().getAllCategories().first()
                            val fruit = categories.find { it.name == "Fruits" }
                            val vegetable = categories.find { it.name == "Vegetables" }

                            if (fruit != null && vegetable != null) {
                                database.foodDao().insert(Food(name = "Mango", price = 100f, categoryId = fruit.id))
                                database.foodDao().insert(Food(name = "Apple", price = 50f, categoryId = fruit.id))
                                database.foodDao().insert(Food(name = "Carrot", price = 30f, categoryId = vegetable.id))
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("AppDatabase", "Error", e)
                    }
                }
            }
        }
    }
}