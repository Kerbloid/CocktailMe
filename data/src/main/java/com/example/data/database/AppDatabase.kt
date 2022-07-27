package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.entity.DrinkConverter
import com.example.data.entity.DrinkLocal

@Database(
    entities = [DrinkLocal::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DrinkConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val drinksDao: DrinkDao
}