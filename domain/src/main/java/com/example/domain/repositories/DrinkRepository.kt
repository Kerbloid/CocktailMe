package com.example.domain.repositories

import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.entities.DrinkList

interface DrinkRepository {
    suspend fun getRemotePopularCocktails(): Result<List<Drink>>
}