package com.example.cocktailme.di

import com.example.data.api.DrinkApiExecutor
import com.example.data.mappers.DrinkApiResponseMapper
import com.example.data.mappers.DrinkLocalToDrinkMapper
import com.example.data.mappers.DrinkToDrinkLocalMapper
import com.example.data.repositories.local.LocalDataSource
import com.example.data.repositories.local.LocalDataSourceImpl
import com.example.data.repositories.local.LocalRepositoryImpl
import com.example.data.repositories.remote.RemoteDataSourceImpl
import com.example.data.repositories.remote.RemoteRepositoryImpl
import com.example.data.repositories.remote.RemoteDataSource
import com.example.domain.repositories.LocalRepository
import com.example.domain.repositories.RemoteRepository
import org.koin.dsl.module

val dataModule = module {

    single<DrinkApiResponseMapper> { DrinkApiResponseMapper() }
    single<DrinkToDrinkLocalMapper> { DrinkToDrinkLocalMapper() }
    single<DrinkLocalToDrinkMapper> { DrinkLocalToDrinkMapper() }

    factory<DrinkApiExecutor> {
        DrinkApiExecutor(mapper = get())
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(service = get(), drinkApiExecutor = get())
    }

    single<RemoteRepository> {
        RemoteRepositoryImpl(remoteDataSource = get())
    }

    single<LocalDataSource> {
        LocalDataSourceImpl(dao = get())
    }

    single<LocalRepository> {
        LocalRepositoryImpl(
            localDataSource = get(),
            drinkLocalToDrinkMapper = get(),
            drinkToDrinkLocalMapper = get()
        )
    }
}