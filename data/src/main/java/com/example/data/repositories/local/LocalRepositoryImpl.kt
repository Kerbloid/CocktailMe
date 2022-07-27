package com.example.data.repositories.local

import com.example.data.entity.DrinkLocal
import com.example.data.mappers.DrinkLocalToDrinkMapper
import com.example.data.mappers.DrinkToDrinkLocalMapper
import com.example.domain.entities.Drink
import com.example.domain.repositories.LocalRepository

class LocalRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val drinkToDrinkLocalMapper: DrinkToDrinkLocalMapper,
    private val drinkLocalToDrinkMapper: DrinkLocalToDrinkMapper
    ): LocalRepository {

    override suspend fun saveToFavorite(drink: Drink) {
        localDataSource.saveToFavorite(drinkToDrinkLocalMapper.drinkToLocalDrink(drink))
    }

    override suspend fun removeFromFavorite(id: String) {
        localDataSource.removeFromFavorite(id)
    }

    override suspend fun getFavorites(): List<Drink?> {
        return localDataSource.getFavorites().map { drinkLocalToDrinkMapper.drinkLocalToDrink(it) }
    }

    override suspend fun getLocalCocktailById(id: String): Drink? {
        return drinkLocalToDrinkMapper.drinkLocalToDrink(localDataSource.getLocalCocktailById(id))
    }
}