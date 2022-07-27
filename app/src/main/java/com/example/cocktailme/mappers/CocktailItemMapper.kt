package com.example.cocktailme.mappers

import com.example.cocktailme.entities.CocktailItem
import com.example.domain.entities.Drink

class CocktailItemMapper {
    fun fromDrinkItemsToCocktailItems(
        drinks: List<Drink?>
    ): List<CocktailItem> {
        if (drinks.isNullOrEmpty()) return listOf()
        val cocktails = arrayListOf<CocktailItem>()
        drinks.forEach { drink ->
            with(drink) {
                cocktails.add(
                    CocktailItem(
                        id = this?.id,
                        name = this?.name,
                        alcoholic = this?.alcoholic,
                        image = this?.drinkThumb,
                        IBA = this?.IBA,
                        ingredients = if (this == null) {
                            emptyList()
                        } else {
                            getIngredientsFromDrink(this).filterNotNull()
                        }
                    )
                )
            }
        }
        return cocktails
    }

    private fun getIngredientsFromDrink(drink: Drink): List<String?> {
        return listOf(
            drink.ingredient1,
            drink.ingredient2,
            drink.ingredient3,
            drink.ingredient4 as? String,
            drink.ingredient5 as? String,
            drink.ingredient6 as? String,
            drink.ingredient7 as? String,
            drink.ingredient8 as? String,
            drink.ingredient9 as? String,
            drink.ingredient10 as? String,
            drink.ingredient11 as? String,
            drink.ingredient12 as? String,
            drink.ingredient13 as? String,
            drink.ingredient14 as? String,
            drink.ingredient15 as? String
        )
    }
}