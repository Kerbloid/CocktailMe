package com.example.data.repositories

import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.repositories.DrinkRepository

class DrinkRepositoryImpl (private val remoteDataSource: DrinksRemoteDataSource) :
    DrinkRepository {

    override suspend fun getRemoteRandomCocktails(): Result<List<Drink>> {
        return remoteDataSource.getRandomDrinks()
    }

    override suspend fun getRemotePopularCocktails(): Result<List<Drink>> {
        return remoteDataSource.getPopular()
    }

    override suspend fun getRemoteLatestCocktails(): Result<List<Drink>> {
        return  remoteDataSource.getLatest()
    }
}