package com.example.data.mappers

import com.example.data.api.DrinkApiResponse
import com.example.domain.entities.Drink

class DrinkApiResponseMapper {
    fun toDrinkList(response: DrinkApiResponse): List<Drink> {
        return response.drinks.map {
            Drink(
                idDrink = it.idDrink,
                strDrink = it.strDrink,
                strDrinkThumb = it.strDrinkThumb
            )
        }
    }
}