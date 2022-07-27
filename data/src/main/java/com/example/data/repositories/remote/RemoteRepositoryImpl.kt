package com.example.data.repositories.remote

import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.repositories.RemoteRepository

class RemoteRepositoryImpl (private val remoteDataSource: RemoteDataSource) :
    RemoteRepository {

    override suspend fun getRemoteRandomCocktails(): Result<List<Drink>> {
        return remoteDataSource.getRandomDrinks()
    }

    override suspend fun getRemotePopularCocktails(): Result<List<Drink>> {
        return remoteDataSource.getPopular()
    }

    override suspend fun getRemoteLatestCocktails(): Result<List<Drink>> {
        return  remoteDataSource.getLatest()
    }

    override suspend fun getRemoteCocktailById(id: String): Result<List<Drink>> {
        return remoteDataSource.getCocktailById(id)
    }

    override suspend fun getCocktailByName(query: String): Result<List<Drink>> {
        return remoteDataSource.searchCocktailByName(query)
    }
}