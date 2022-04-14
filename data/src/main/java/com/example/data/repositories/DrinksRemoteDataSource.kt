package com.example.data.repositories

import com.example.domain.common.Result
import com.example.domain.entities.Drink

interface DrinksRemoteDataSource {
    suspend fun getRandomDrinks(): Result<List<Drink>>
    suspend fun getPopular(): Result<List<Drink>>
    suspend fun getLatest(): Result<List<Drink>>
    suspend fun getCocktailById(id: String): Result<List<Drink>>
}