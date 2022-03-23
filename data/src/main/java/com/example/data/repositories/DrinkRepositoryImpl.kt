package com.example.data.repositories

import com.example.domain.repositories.DrinkRepository
import com.example.domain.common.Result
import com.example.domain.entities.Drink

class DrinkRepositoryImpl (private val remoteDataSource: DrinksRemoteDataSource) :
    DrinkRepository {

    override suspend fun getRemotePopularCocktails(): Result<List<Drink>> {
        return remoteDataSource.getRandomDrinks()
    }
}