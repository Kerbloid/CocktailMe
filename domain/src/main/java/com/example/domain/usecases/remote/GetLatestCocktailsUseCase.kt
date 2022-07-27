package com.example.domain.usecases.remote

import com.example.domain.repositories.RemoteRepository

class GetLatestCocktailsUseCase(private val remoteRepository: RemoteRepository) {
    suspend fun getLatestCocktails() = remoteRepository.getRemoteLatestCocktails()
}