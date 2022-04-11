package com.example.cocktailme.presentation.mappers

import android.util.Log
import com.example.cocktailme.presentation.entities.Cocktail
import com.example.domain.entities.Drink

class CocktailMapper {
    fun fromDrinkToCocktail(
        drinks: List<Drink>
    ): List<Cocktail> {
        val cocktails = arrayListOf<Cocktail>()
        drinks.forEach { drink ->
            with(drink) {
                cocktails.add(
                    Cocktail(
                        idDrink,
                        strDrink,
                        strDrinkThumb
                    )
                )
            }
        }
        return cocktails
    }

    fun fromCocktailToDrink(cocktail: Cocktail): Drink {
        return Drink(idDrink = cocktail.id, strDrink = cocktail.name, strDrinkThumb = cocktail.image)
    }
}