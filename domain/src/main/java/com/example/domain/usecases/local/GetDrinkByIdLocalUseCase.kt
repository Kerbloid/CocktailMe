package com.example.domain.usecases.local

import com.example.domain.entities.Drink
import com.example.domain.repositories.LocalRepository

class GetDrinkByIdLocalUseCase(private val localRepository: LocalRepository) {
    suspend fun getDrinkById(id: String): Drink? = localRepository.getLocalCocktailById(id)
}