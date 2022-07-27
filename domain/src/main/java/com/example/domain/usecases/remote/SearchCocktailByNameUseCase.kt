package com.example.domain.usecases.remote

import com.example.domain.repositories.RemoteRepository

class SearchCocktailByNameUseCase(private val remoteRepository: RemoteRepository) {
    suspend fun getCocktailByName(query: String) = remoteRepository.getCocktailByName(query)
}