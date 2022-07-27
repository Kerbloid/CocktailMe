package com.example.cocktailme.di

import com.example.domain.usecases.local.GetDrinkByIdLocalUseCase
import com.example.domain.usecases.local.GetFavoritesUseCase
import com.example.domain.usecases.local.RemoveFromFavoriteUseCase
import com.example.domain.usecases.local.SaveToFavoriteUseCase
import com.example.domain.usecases.remote.*
import org.koin.dsl.module

val domainModule = module {
    //Remote
    factory<GetPopularCocktailsUseCase> { GetPopularCocktailsUseCase(remoteRepository = get()) }
    factory<GetLatestCocktailsUseCase> { GetLatestCocktailsUseCase(remoteRepository = get()) }
    factory<GetRandomCocktailsUseCase> { GetRandomCocktailsUseCase(remoteRepository = get()) }
    factory<GetCocktailByIdRemoteUseCase> { GetCocktailByIdRemoteUseCase(remoteRepository = get()) }
    factory<SearchCocktailByNameUseCase> { SearchCocktailByNameUseCase(remoteRepository = get()) }

    //Local
    factory<SaveToFavoriteUseCase> { SaveToFavoriteUseCase(localRepository = get()) }
    factory<RemoveFromFavoriteUseCase> { RemoveFromFavoriteUseCase(localRepository = get()) }
    factory<GetFavoritesUseCase> { GetFavoritesUseCase(localRepository = get()) }
    factory<GetDrinkByIdLocalUseCase> { GetDrinkByIdLocalUseCase(localRepository = get()) }
}