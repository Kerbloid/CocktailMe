package com.example.data.api

import retrofit2.Response
import retrofit2.http.GET

interface DrinkApi {
    @GET("randomselection.php")
    suspend fun getRandomDrinks():  Response<DrinkApiResponse>

//    @GET("random.php")
//    fun getRandomCocktail(): Response<DrinkList>
//
    @GET("popular.php")
    suspend fun getPopular(): Response<DrinkApiResponse>

    @GET("latest.php")
    suspend fun getLatest(): Response<DrinkApiResponse>
//
//    @GET("search.php")
//    fun searchByName(@Query("s") name: String)
//
//    @GET("filter.php")
//    fun searchByIngredient(@Query("i") ingredient: String)
}