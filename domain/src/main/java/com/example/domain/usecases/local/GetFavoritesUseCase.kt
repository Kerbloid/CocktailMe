package com.example.domain.usecases.local

import com.example.domain.repositories.LocalRepository

class GetFavoritesUseCase(private val localRepository: LocalRepository) {
    suspend fun getFavorites() = localRepository.getFavorites()
}