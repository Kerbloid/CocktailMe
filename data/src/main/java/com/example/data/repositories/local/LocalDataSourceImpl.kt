package com.example.data.repositories.local

import com.example.data.database.DrinkDao
import com.example.data.entity.DrinkLocal
import com.example.data.mappers.DrinkToDrinkLocalMapper
import com.example.domain.entities.Drink

class LocalDataSourceImpl(
    private val dao: DrinkDao
): LocalDataSource {
    override suspend fun saveToFavorite(drink: DrinkLocal) {
        dao.saveToFavorite(drink)
    }

    override suspend fun removeFromFavorite(id: String) {
        dao.removeFromFavorite(id)
    }

    override suspend fun getFavorites(): List<DrinkLocal> {
        return dao.getFavorites()
    }

    override suspend fun getLocalCocktailById(id: String): DrinkLocal? {
        return dao.getDrinkByIdLocal(id)
    }
}