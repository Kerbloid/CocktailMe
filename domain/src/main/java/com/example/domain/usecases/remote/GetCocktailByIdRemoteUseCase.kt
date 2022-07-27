package com.example.domain.usecases.remote

import com.example.domain.repositories.RemoteRepository

class GetCocktailByIdRemoteUseCase(private val remoteRepository: RemoteRepository) {
    suspend fun getCocktailById(id: String) = remoteRepository.getRemoteCocktailById(id)
}