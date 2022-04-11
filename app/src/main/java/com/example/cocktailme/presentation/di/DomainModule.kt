package com.example.cocktailme.presentation.di

import com.example.domain.usecases.GetRandomCocktailsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetRandomCocktailsUseCase> { GetRandomCocktailsUseCase(drinkRepository = get()) }
}