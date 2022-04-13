package com.example.domain.usecases

import com.example.domain.repositories.DrinkRepository

class GetRandomCocktailsUseCase(private val drinkRepository: DrinkRepository) {
    suspend fun getRandomCocktails() = drinkRepository.getRemoteRandomCocktails()
}