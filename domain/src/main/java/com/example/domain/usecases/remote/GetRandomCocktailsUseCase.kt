package com.example.domain.usecases.remote

import com.example.domain.repositories.RemoteRepository

class GetRandomCocktailsUseCase(private val remoteRepository: RemoteRepository) {
    suspend fun getRandomCocktails() = remoteRepository.getRemoteRandomCocktails()
}