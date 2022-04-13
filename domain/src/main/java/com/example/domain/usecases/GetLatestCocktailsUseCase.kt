package com.example.domain.usecases

import com.example.domain.repositories.DrinkRepository

class GetLatestCocktailsUseCase(private val drinkRepository: DrinkRepository) {
    suspend fun getLatestCocktails() = drinkRepository.getRemoteLatestCocktails()
}