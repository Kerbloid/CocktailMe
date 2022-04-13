package com.example.cocktailme.presentation.di

import com.example.cocktailme.presentation.mappers.CocktailMapper
import com.example.cocktailme.presentation.ui.home.HomeCocktailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CocktailMapper> { CocktailMapper() }

    viewModel<HomeCocktailsViewModel> {
        HomeCocktailsViewModel(
            getPopularCocktailsUseCase = get(),
            getLatestCocktailsUseCase = get(),
            getRandomCocktailsUseCase = get(),
            mapper = get()
        )
    }
}