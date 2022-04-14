package com.example.cocktailme.presentation.di

import com.example.data.api.DrinkApiExecutor
import com.example.data.mappers.DrinkApiResponseMapper
import com.example.data.repositories.DrinkRemoteDataSourceImpl
import com.example.data.repositories.DrinkRepositoryImpl
import com.example.data.repositories.DrinksRemoteDataSource
import com.example.domain.repositories.DrinkRepository
import org.koin.dsl.module

val dataModule = module {

    single<DrinkApiResponseMapper> { DrinkApiResponseMapper() }

    factory<DrinkApiExecutor> {
        DrinkApiExecutor(mapper = get())
    }

    single<DrinksRemoteDataSource> {
        DrinkRemoteDataSourceImpl(service = get(), drinkApiExecutor = get())
    }

    single<DrinkRepository> {
        DrinkRepositoryImpl(remoteDataSource = get())
    }
}