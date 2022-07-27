package com.example.domain.repositories

import com.example.domain.entities.Drink

interface LocalRepository {
    suspend fun saveToFavorite(drink: Drink)
    suspend fun removeFromFavorite(id: String)
    suspend fun getFavorites(): List<Drink?>
    suspend fun getLocalCocktailById(id: String): Drink?
}