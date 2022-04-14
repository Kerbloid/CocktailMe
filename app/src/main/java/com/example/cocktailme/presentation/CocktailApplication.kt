package com.example.cocktailme.presentation

import android.app.Application
import com.example.cocktailme.presentation.di.*
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