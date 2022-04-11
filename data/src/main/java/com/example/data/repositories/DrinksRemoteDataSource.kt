package com.example.data.repositories

import com.example.domain.common.Result
import com.example.domain.entities.Drink

interface DrinksRemoteDataSource {
    suspend fun getRandomDrinks(): Result<List<Drink>>
}