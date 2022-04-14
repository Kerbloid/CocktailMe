package com.example.domain.repositories

import com.example.domain.common.Result
import com.example.domain.entities.Drink

interface DrinkRepository {
    suspend fun getRemoteRandomCocktails(): Result<List<Drink>>
    suspend fun getRemotePopularCocktails(): Result<List<Drink>>
    suspend fun getRemoteLatestCocktails(): Result<List<Drink>>
    suspend fun getRemoteCocktailById(id: String): Result<List<Drink>>
}