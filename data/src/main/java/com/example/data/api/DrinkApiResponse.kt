package com.example.data.api

import com.example.domain.entities.Drink
import com.google.gson.annotations.SerializedName

class DrinkApiResponse(val drinks: List<Item>)

data class Item(
    @SerializedName("idDrink")
    val idDrink: String? = null,
    @SerializedName("strDrink")
    val strDrink: String? = null,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String? = null,
    @SerializedName("strIngredient1")
    val strIngredient1: String? = null,
    @SerializedName("strIngredient2")
    val strIngredient2: String? = null,
    @SerializedName("strIngredient3")
    val strIngredient3: String? = null,
    @SerializedName("strIngredient4")
    val strIngredient4: Any? = null,
    @SerializedName("strIngredient5")
    val strIngredient5: Any? = null,
    @SerializedName("strInstructions")
    val strInstructions: String? = null,
    @SerializedName("strMeasure1")
    val strMeasure1: String? = null,
    @SerializedName("strMeasure2")
    val strMeasure2: String? = null,
    @SerializedName("strMeasure3")
    val strMeasure3: String? = null,
    @SerializedName("strMeasure4")
    val strMeasure4: Any? = null,
    @SerializedName("strMeasure5")
    val strMeasure5: Any? = null
)