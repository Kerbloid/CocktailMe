package com.example.domain.usecases

import com.example.domain.repositories.DrinkRepository

class GetPopularCocktailsUseCase(private val drinkRepository: DrinkRepository) {
    suspend fun getPopularCocktails() = drinkRepository.getRemotePopularCocktails()
}