package com.example.cocktailme.di

import android.app.Application
import androidx.room.Room
import com.example.data.database.AppDatabase
import com.example.data.database.DrinkDao
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "drinksDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDrinksDao(database: AppDatabase): DrinkDao {
        return  database.drinksDao
    }

    single { provideDatabase(get()) }
    single { provideDrinksDao(get()) }
}