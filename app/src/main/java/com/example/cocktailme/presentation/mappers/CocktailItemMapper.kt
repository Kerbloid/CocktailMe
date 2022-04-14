package com.example.cocktailme.presentation.mappers

import com.example.cocktailme.presentation.entities.CocktailItem
import com.example.domain.entities.Drink

class CocktailItemMapper {
    fun fromDrinkItemsToCocktailItems(
        drinks: List<Drink>
    ): List<CocktailItem> {
        val cocktails = arrayListOf<CocktailItem>()
        drinks.forEach { drink ->
            with(drink) {
                cocktails.add(
                    CocktailItem(
                        id = id,
                        name = name,
                        alcoholic = alcoholic,
                        image = drinkThumb
                    )
                )
            }
        }
        return cocktails
    }
}