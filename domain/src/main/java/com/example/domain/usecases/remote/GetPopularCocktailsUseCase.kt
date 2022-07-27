package com.example.domain.usecases.remote

import com.example.domain.repositories.RemoteRepository

class GetPopularCocktailsUseCase(private val remoteRepository: RemoteRepository) {
    suspend fun getPopularCocktails() = remoteRepository.getRemotePopularCocktails()
}