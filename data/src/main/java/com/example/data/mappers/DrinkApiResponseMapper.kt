package com.example.data.mappers

import android.util.Log
import com.example.data.api.DrinkApiResponse
import com.example.domain.entities.Drink

class DrinkApiResponseMapper {
    fun toDrinkItemsList(response: DrinkApiResponse): List<Drink> {
        return response.drinks.map { item ->
            with(item) {
                Drink(
                    id = id,
                    name = name,
                    drinkThumb = drinkThumb,
                    alcoholic = alcoholic
                )
            }
        }
    }

    fun toDrinkList(response: DrinkApiResponse): List<Drink> {
        return response.drinks.map { item ->
            with(item) {
                Drink(
                    id = id,
                    alcoholic = alcoholic,
                    category = category,
                    name = name,
                    drinkAlternate = drinkAlternate,
                    drinkThumb = drinkThumb,
                    glass = glass,
                    ingredient1 = ingredient1,
                    ingredient2 = ingredient2,
                    ingredient3 = ingredient3,
                    ingredient4 = ingredient4,
                    ingredient5 = ingredient5,
                    instructions = instructions,
                    measure1 = measure1,
                    measure2 = measure2,
                    measure3 = measure3,
                    measure4 = measure4,
                    measure5 = measure5,
                    tags = tags,
                    video = video
                ).also { Log.d("SASA", "$it") }
            }
        }
    }
}