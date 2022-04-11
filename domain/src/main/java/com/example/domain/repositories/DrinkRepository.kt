package com.example.domain.repositories

import com.example.domain.common.Result
import com.example.domain.entities.Drink

interface DrinkRepository {
    suspend fun getRemotePopularCocktails(): Result<List<Drink>>
}