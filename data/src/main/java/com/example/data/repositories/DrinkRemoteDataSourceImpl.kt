package com.example.data.repositories

import com.example.data.api.DrinkApi
import com.example.data.mappers.DrinkApiResponseMapper
import com.example.domain.entities.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.domain.common.Result

class DrinkRemoteDataSourceImpl(
    private val service: DrinkApi,
    private val mapper: DrinkApiResponseMapper
) : DrinksRemoteDataSource {

    override suspend fun getRandomDrinks(): Result<List<Drink>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getRandomDrinks()
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toDrinkList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }

    override suspend fun getPopular(): Result<List<Drink>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getPopular()
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toDrinkList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }


    override suspend fun getLatest(): Result<List<Drink>> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getLatest()
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toDrinkList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}
