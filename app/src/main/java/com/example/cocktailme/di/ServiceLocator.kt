package com.example.cocktailme.di

import android.content.Context
import com.example.data.API_KEY
import com.example.data.BASE_URL
import com.example.data.api.NetworkModule
import com.example.data.mappers.DrinkApiResponseMapper
import com.example.data.mappers.DrinkEntityMapper
import com.example.data.repositories.DrinkRepositoryImpl
import com.example.data.repositories.DrinkRemoteDataSourceImpl

object ServiceLocator {
//    private var database: BooksDatabase? = null
    private val networkModule by lazy {
        NetworkModule()
    }
    private val drinkEntityMapper by lazy {
        DrinkEntityMapper()
    }

    @Volatile
    var drinkRepository: DrinkRepositoryImpl? = null

    fun provideDrinksRepository(context: Context): DrinkRepositoryImpl {
        // useful because this method can be accessed by multiple threads
        synchronized(this) {
            return drinkRepository ?: createDrinksRepository(context)
        }
    }

    private fun createDrinksRepository(context: Context): DrinkRepositoryImpl {
        val newRepo =
            DrinkRepositoryImpl(
//                createBooksLocalDataSource(context),
                DrinkRemoteDataSourceImpl(
                    networkModule.createBooksApi("${BASE_URL}$API_KEY/"),
                    DrinkApiResponseMapper()
                )
            )
        drinkRepository = newRepo
        return newRepo
    }

//    private fun createBooksLocalDataSource(context: Context): BooksLocalDataSource {
//        val database = database ?: createDataBase(context)
//        return BooksLocalDataSourceImpl(
//            database.bookDao(),
//            Dispatchers.IO,
//            bookEntityMapper
//        )
//    }

//    private fun createDataBase(context: Context): BooksDatabase {
//        val result = BooksDatabase.getDatabase(context)
//        database = result
//        return result
//    }
}