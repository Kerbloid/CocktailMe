package com.example.data.repositories.local

import com.example.data.entity.DrinkLocal
import com.example.domain.entities.Drink

interface LocalDataSource {
    suspend fun saveToFavorite(drink: DrinkLocal)
    suspend fun removeFromFavorite(id: String)
    suspend fun getFavorites(): List<DrinkLocal>
    suspend fun getLocalCocktailById(id: String): DrinkLocal?
}