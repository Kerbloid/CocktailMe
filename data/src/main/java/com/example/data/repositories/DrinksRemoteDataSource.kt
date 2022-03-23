package com.example.data.repositories

import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.entities.DrinkList

interface DrinksRemoteDataSource {
    suspend fun getRandomDrinks(): Result<List<Drink>>
}