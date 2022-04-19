package com.example.cocktailme.mappers

import android.util.Log
import com.example.cocktailme.entities.Cocktail
import com.example.domain.entities.Drink

class CocktailMapper {
    fun fromDrinkToCocktail(
        drink: Drink
    ): Cocktail {
        return with(drink) {
            Cocktail(
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
                video = video,
                IBA = IBA
            )
        }
    }
}