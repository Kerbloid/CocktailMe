package com.example.data.api

import com.example.domain.entities.Drink
import com.example.domain.entities.DrinkList
import retrofit2.Response
import retrofit2.http.GET

interface DrinkApi {
    @GET("randomselection.php")
    suspend fun getRandomDrinks():  Response<DrinkApiResponse>

//    @GET("random.php")
//    fun getRandomCocktail(): Response<DrinkList>
//
//    @GET("popular.php")
//    fun getPopular(): Call<DrinkList>
//
//    @GET("latest.php")
//    fun getLatest(): Call<DrinkList>
//
//    @GET("search.php")
//    fun searchByName(@Query("s") name: String)
//
//    @GET("filter.php")
//    fun searchByIngredient(@Query("i") ingredient: String)
}