package com.example.data.mappers

import com.example.data.entity.DrinkLocal
import com.example.domain.entities.Drink

class DrinkLocalToDrinkMapper {
    fun drinkLocalToDrink(drinkLocal: DrinkLocal?): Drink? {
        if (drinkLocal == null) return null
        return with(drinkLocal) {
            Drink(
                dateModified = dateModified,
                id = id,
                alcoholic = alcoholic,
                category = category,
                creativeCommonsConfirmed = creativeCommonsConfirmed,
                name = name,
                drinkAlternate = drinkAlternate,
                drinkThumb = drinkThumb,
                glass = glass,
                IBA = IBA,
                imageAttribution = imageAttribution,
                imageSource = imageSource,
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
                ingredient11 = ingredient11,
                ingredient12 = ingredient12,
                ingredient13 = ingredient13,
                ingredient14 = ingredient14,
                ingredient15 = ingredient15,
                instructions = instructions,
                instructionsDE = instructionsDE,
                instructionsES = instructionsES,
                instructionsFR = instructionsFR,
                instructionsIT = instructionsIT,
                instructionsZHHANS = instructionsZHHANS,
                instructionsZHHANT = instructionsZHHANT,
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
                measure11 = measure11,
                measure12 = measure12,
                measure13 = measure13,
                measure14 = measure14,
                measure15 = measure15,
                tags = tags,
                video = video
            )
        }
    }
}