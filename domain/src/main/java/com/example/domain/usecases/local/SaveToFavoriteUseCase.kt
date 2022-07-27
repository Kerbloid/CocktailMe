package com.example.domain.usecases.local

import com.example.domain.entities.Drink
import com.example.domain.repositories.LocalRepository

class SaveToFavoriteUseCase(private val localRepository: LocalRepository) {
    suspend fun saveToFavorite(drink: Drink) = localRepository.saveToFavorite(drink)
}