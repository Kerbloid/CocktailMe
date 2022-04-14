package com.example.cocktailme.presentation.di

import com.example.domain.usecases.GetCocktailByIdUseCase
import com.example.domain.usecases.GetLatestCocktailsUseCase
import com.example.domain.usecases.GetPopularCocktailsUseCase
import com.example.domain.usecases.GetRandomCocktailsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetPopularCocktailsUseCase> { GetPopularCocktailsUseCase(drinkRepository = get()) }
    factory<GetLatestCocktailsUseCase> { GetLatestCocktailsUseCase(drinkRepository = get()) }
    factory<GetRandomCocktailsUseCase> { GetRandomCocktailsUseCase(drinkRepository = get()) }
    factory<GetCocktailByIdUseCase> { GetCocktailByIdUseCase(drinkRepository = get()) }
}