package com.example.lab3.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lab3.dao.CategoryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.lab3.entity.Food
import com.example.lab3.dao.FoodDao
import kotlinx.coroutines.Dispatchers


@Database(entities = [Food::class, com.example.lab3.entity.Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun categoryDao(): CategoryDao

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

        private class FoodItemCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(scope.coroutineContext + Dispatchers.IO).launch {
                    INSTANCE?.let { database ->
                        scope.launch {
                            database.categoryDao()
                                .insert(com.example.lab3.entity.Category(0, "Fruits"))
                            database.categoryDao()
                                .insert(com.example.lab3.entity.Category(1, "Vegetables"))

                            database.foodDao().insert(Food(0, "Mango", 100f, 1))
                            database.foodDao().insert(Food(0, "Apple", 50f, 1))
                            database.foodDao().insert(Food(0, "Carrot", 30f, 0))
                        }
                    }
                }
            }
        }
    }
}
