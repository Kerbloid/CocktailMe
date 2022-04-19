package com.example.cocktailme.mappers

import com.example.cocktailme.entities.CocktailItem
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
                        image = drinkThumb,
                        IBA = IBA
                    )
                )
            }
        }
        return cocktails
    }
}