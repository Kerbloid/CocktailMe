package com.example.data.api

import com.example.data.mappers.DrinkApiResponseMapper
import com.example.domain.common.Result
import com.example.domain.entities.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DrinkApiExecutor(private val mapper: DrinkApiResponseMapper) {

    suspend fun execute(response: Response<DrinkApiResponse>): Result<List<Drink>> =
        withContext(Dispatchers.IO) {
            try {
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