package com.example.cocktailme.di

import com.example.cocktailme.mappers.CocktailItemMapper
import com.example.cocktailme.mappers.CocktailMapper
import com.example.cocktailme.presentation.ui.cocktailInfo.CocktailInfoViewModel
import com.example.cocktailme.presentation.ui.home.HomeCocktailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CocktailItemMapper> { CocktailItemMapper() }
    single<CocktailMapper> { CocktailMapper() }

    viewModel<HomeCocktailsViewModel> {
        HomeCocktailsViewModel(
            getPopularCocktailsUseCase = get(),
            getLatestCocktailsUseCase = get(),
            getRandomCocktailsUseCase = get(),
            itemMapper = get(),
            networkIdentifier = get()
        )
    }

    viewModel<CocktailInfoViewModel> {
        CocktailInfoViewModel(
            getCocktailByIdUseCase = get(),
            cocktailMapper = get(),
            networkIdentifier = get()
        )
    }
}