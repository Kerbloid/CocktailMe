package com.example.data.database

import androidx.room.*
import com.example.data.entity.DrinkLocal
import com.example.domain.entities.Drink

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveToFavorite(drink: DrinkLocal)

    @Query("DELETE FROM drinks WHERE id = :id")
    fun removeFromFavorite(id: String)

    @Query("SELECT * FROM drinks")
    fun getFavorites(): List<DrinkLocal>

    @Query("SELECT * FROM drinks WHERE id = :id")
    fun getDrinkByIdLocal(id: String): DrinkLocal?
}