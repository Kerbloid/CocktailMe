package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "drinks")
@TypeConverters(DrinkConverter::class)
data class DrinkLocal(
    var dateModified: String? = null,
    @PrimaryKey
    var id: String,
    var alcoholic: String? = null,
    var category: String? = null,
    var creativeCommonsConfirmed: String? = null,
    var name: String? = null,
    var drinkAlternate: Any? = null,
    var drinkThumb: String? = null,
    var glass: String? = null,
    var IBA: String? = null,
    var imageAttribution: String? = null,
    var imageSource: String? = null,
    var ingredient1: String? = null,
    var ingredient2: String? = null,
    var ingredient3: String? = null,
    var ingredient4: Any? = null,
    var ingredient5: Any? = null,
    var ingredient6: Any? = null,
    var ingredient7: Any? = null,
    var ingredient8: Any? = null,
    var ingredient9: Any? = null,
    var ingredient10: Any? = null,
    var ingredient11: Any? = null,
    var ingredient12: Any? = null,
    var ingredient13: Any? = null,
    var ingredient14: Any? = null,
    var ingredient15: Any? = null,
    var instructions: String? = null,
    var instructionsDE: String? = null,
    var instructionsES: Any? = null,
    var instructionsFR: Any? = null,
    var instructionsIT: String? = null,
    var instructionsZHHANS: Any? = null,
    var instructionsZHHANT: Any? = null,
    var measure1: String? = null,
    var measure2: String? = null,
    var measure3: String? = null,
    var measure4: Any? = null,
    var measure5: Any? = null,
    var measure6: Any? = null,
    var measure7: Any? = null,
    var measure8: Any? = null,
    var measure9: Any? = null,
    var measure10: Any? = null,
    var measure11: Any? = null,
    var measure12: Any? = null,
    var measure13: Any? = null,
    var measure14: Any? = null,
    var measure15: Any? = null,
    var tags: String? = null,
    var video: String? = null
)