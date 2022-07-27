package com.example.data.entity

import androidx.room.TypeConverter

class DrinkConverter {

    @TypeConverter
    fun fromStringToAny(value: String?): Any? {
        return value
    }

    @TypeConverter
    fun fromAnyToString(value: Any?): String? {
        return value?.toString()
    }
}