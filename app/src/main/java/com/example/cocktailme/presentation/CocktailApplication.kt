package com.example.cocktailme.presentation

import android.app.Application
import com.example.cocktailme.presentation.di.*
import com.example.cocktailme.presentation.mappers.CocktailMapper
import com.example.data.repositories.DrinkRepositoryImpl
import com.example.domain.usecases.GetRandomCocktailsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CocktailApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CocktailApplication)
            modules(appModule, dataModule, domainModule, networkModule)
        }
    }
}