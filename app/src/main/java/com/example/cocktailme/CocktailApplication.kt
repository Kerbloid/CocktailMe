package com.example.cocktailme

import android.app.Application
import com.example.cocktailme.di.ServiceLocator
import com.example.cocktailme.mappers.CocktailMapper
import com.example.data.repositories.DrinkRepositoryImpl
import com.example.domain.usecases.GetRandomCocktailsUseCase

class CocktailApplication: Application() {
    private val drinkRepository: DrinkRepositoryImpl
        get() = ServiceLocator.provideDrinksRepository(this)

    val getRandomCocktailsUseCase: GetRandomCocktailsUseCase
        get() = GetRandomCocktailsUseCase(drinkRepository)

//    val getBookmarksUseCase: GetBookmarksUseCase
//        get() = GetBookmarksUseCase(booksRepository)
//
//    val bookmarkBooksUseCase: BookmarkBookUseCase
//        get() = BookmarkBookUseCase(booksRepository)
//
//    val unbookmarkBookUseCase: UnbookmarkBookUseCase
//        get() = UnbookmarkBookUseCase(booksRepository)

    val cocktailMapper = CocktailMapper()

    override fun onCreate() {
        super.onCreate()
    }
}