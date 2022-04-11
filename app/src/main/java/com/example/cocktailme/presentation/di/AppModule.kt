package com.example.cocktailme.presentation.di

import com.example.cocktailme.presentation.mappers.CocktailMapper
import com.example.cocktailme.presentation.ui.randomCocktails.RandomCocktailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CocktailMapper> { CocktailMapper() }

    viewModel<RandomCocktailsViewModel> {
        RandomCocktailsViewModel(getRandomCocktailsUseCase = get(), mapper = get())
    }
}