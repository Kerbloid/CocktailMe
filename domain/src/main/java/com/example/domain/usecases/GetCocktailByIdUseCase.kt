package com.example.domain.usecases

import com.example.domain.repositories.DrinkRepository

class GetCocktailByIdUseCase(private val drinkRepository: DrinkRepository) {
    suspend fun getCocktailById(id: String) = drinkRepository.getRemoteCocktailById(id)
}