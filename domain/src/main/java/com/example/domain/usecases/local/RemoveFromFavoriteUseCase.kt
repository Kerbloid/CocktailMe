package com.example.domain.usecases.local

import com.example.domain.repositories.LocalRepository

class RemoveFromFavoriteUseCase(private val localRepository: LocalRepository) {
    suspend fun removeFromFavorite(id: String) = localRepository.removeFromFavorite(id)
}