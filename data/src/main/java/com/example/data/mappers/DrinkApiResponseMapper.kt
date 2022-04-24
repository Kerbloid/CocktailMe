package com.example.data.mappers

import com.example.data.api.DrinkApiResponse
import com.example.domain.entities.Drink

class DrinkApiResponseMapper {

    fun toDrinkList(response: DrinkApiResponse): List<Drink> {
        return response.drinks.map { item ->
            with(item) {
                Drink(
                    id = id,
                    alcoholic = alcoholic,
                    category = category,
                    name = name,
                    IBA = IBA,
                    drinkAlternate = drinkAlternate,
                    drinkThumb = drinkThumb,
                    glass = glass,
                    ingredient1 = ingredient1,
                    ingredient2 = ingredient2,
                    ingredient3 = ingredient3,
                    ingredient4 = ingredient4,
                    ingredient5 = ingredient5,
                    ingredient6 = ingredient6,
                    ingredient7 = ingredient7,
                    ingredient8 = ingredient8,
                    ingredient9 = ingredient9,
                    ingredient10 = ingredient10,
                    instructions = instructions,
                    measure1 = measure1,
                    measure2 = measure2,
                    measure3 = measure3,
                    measure4 = measure4,
                    measure5 = measure5,
                    measure6 = measure6,
                    measure7 = measure7,
                    measure8 = measure8,
                    measure9 = measure9,
                    measure10 = measure10,
                    tags = tags,
                    video = video
                )
            }
        }
    }
}