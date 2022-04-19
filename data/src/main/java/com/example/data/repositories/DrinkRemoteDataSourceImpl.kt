package com.example.data.repositories

import com.example.data.api.DrinkApiExecutor
import com.example.data.api.DrinkApi
import com.example.domain.entities.Drink
import com.example.domain.common.Result

class DrinkRemoteDataSourceImpl(
    private val service: DrinkApi,
    private val drinkApiExecutor: DrinkApiExecutor
) : DrinksRemoteDataSource {

    override suspend fun getRandomDrinks(): Result<List<Drink>> =
        drinkApiExecutor.execute(service.getRandomDrinks())

    override suspend fun getPopular(): Result<List<Drink>> =
        drinkApiExecutor.execute(service.getPopular())

    override suspend fun getLatest(): Result<List<Drink>> =
        drinkApiExecutor.execute(service.getLatest())

    override suspend fun getCocktailById(id: String): Result<List<Drink>> =
        drinkApiExecutor.execute(service.getCocktailById(id))
}
