package com.example.data.api

import com.google.gson.annotations.SerializedName

class DrinkApiResponse(val drinks: List<Item>)

data class Item(
    @SerializedName("dateModified")
    val dateModified: String? = null,
    @SerializedName("idDrink")
    val id: String,
    @SerializedName("strAlcoholic")
    val alcoholic: String? = null,
    @SerializedName("strCategory")
    val category: String? = null,
    @SerializedName("strCreativeCommonsConfirmed")
    val creativeCommonsConfirmed: String? = null,
    @SerializedName("strDrink")
    val name: String? = null,
    @SerializedName("strDrinkAlternate")
    val drinkAlternate: Any? = null,
    @SerializedName("strDrinkThumb")
    val drinkThumb: String? = null,
    @SerializedName("strGlass")
    val glass: String? = null,
    @SerializedName("strIBA")
    val IBA: String? = null,
    @SerializedName("strImageAttribution")
    val imageAttribution: String? = null,
    @SerializedName("strImageSource")
    val imageSource: String? = null,
    @SerializedName("strIngredient1")
    val ingredient1: String? = null,
    @SerializedName("strIngredient2")
    val ingredient2: String? = null,
    @SerializedName("strIngredient3")
    val ingredient3: String? = null,
    @SerializedName("strIngredient4")
    val ingredient4: Any? = null,
    @SerializedName("strIngredient5")
    val ingredient5: Any? = null,
    @SerializedName("strIngredient6")
    val ingredient6: Any? = null,
    @SerializedName("strIngredient7")
    val ingredient7: Any? = null,
    @SerializedName("strIngredient8")
    val ingredient8: Any? = null,
    @SerializedName("strIngredient9")
    val ingredient9: Any? = null,
    @SerializedName("strIngredient10")
    val ingredient10: Any? = null,
    @SerializedName("strIngredient11")
    val ingredient11: Any? = null,
    @SerializedName("strIngredient12")
    val ingredient12: Any? = null,
    @SerializedName("strIngredient13")
    val ingredient13: Any? = null,
    @SerializedName("strIngredient14")
    val ingredient14: Any? = null,
    @SerializedName("strIngredient15")
    val ingredient15: Any? = null,
    @SerializedName("strInstructions")
    val instructions: String? = null,
    @SerializedName("strInstructionsDE")
    val instructionsDE: String? = null,
    @SerializedName("strInstructionsES")
    val instructionsES: Any? = null,
    @SerializedName("strInstructionsFR")
    val instructionsFR: Any? = null,
    @SerializedName("strInstructionsIT")
    val instructionsIT: String? = null,
    @SerializedName("strInstructionsZH-HANS")
    val instructionsZHHANS: Any? = null,
    @SerializedName("strInstructionsZH-HANT")
    val instructionsZHHANT: Any? = null,
    @SerializedName("strMeasure1")
    val measure1: String? = null,
    @SerializedName("strMeasure2")
    val measure2: String? = null,
    @SerializedName("strMeasure3")
    val measure3: String? = null,
    @SerializedName("strMeasure4")
    val measure4: Any? = null,
    @SerializedName("strMeasure5")
    val measure5: Any? = null,
    @SerializedName("strMeasure6")
    val measure6: Any? = null,
    @SerializedName("strMeasure7")
    val measure7: Any? = null,
    @SerializedName("strMeasure8")
    val measure8: Any? = null,
    @SerializedName("strMeasure9")
    val measure9: Any? = null,
    @SerializedName("strMeasure10")
    val measure10: Any? = null,
    @SerializedName("strMeasure11")
    val measure11: Any? = null,
    @SerializedName("strMeasure12")
    val measure12: Any? = null,
    @SerializedName("strMeasure13")
    val measure13: Any? = null,
    @SerializedName("strMeasure14")
    val measure14: Any? = null,
    @SerializedName("strMeasure15")
    val measure15: Any? = null,
    @SerializedName("strTags")
    val tags: String? = null,
    @SerializedName("strVideo")
    val video: String? = null
)