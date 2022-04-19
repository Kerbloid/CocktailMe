package com.example.cocktailme.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.R
import com.example.cocktailme.common.NetworkIdentifier
import com.example.cocktailme.entities.CocktailItem
import com.example.cocktailme.mappers.CocktailItemMapper
import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.usecases.GetLatestCocktailsUseCase
import com.example.domain.usecases.GetPopularCocktailsUseCase
import com.example.domain.usecases.GetRandomCocktailsUseCase
import kotlinx.coroutines.launch

class HomeCocktailsViewModel(
    private val getPopularCocktailsUseCase: GetPopularCocktailsUseCase,
    private val getLatestCocktailsUseCase: GetLatestCocktailsUseCase,
    private val getRandomCocktailsUseCase: GetRandomCocktailsUseCase,
    private val itemMapper: CocktailItemMapper,
    private val networkIdentifier: NetworkIdentifier
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _popularCocktails = MutableLiveData<List<CocktailItem>>()
    val popularCocktails = _popularCocktails
    private val _latestCocktails = MutableLiveData<List<CocktailItem>>()
    val latestCocktails = _latestCocktails
    private val _randomCocktails = MutableLiveData<List<CocktailItem>>()
    val randomCocktails = _randomCocktails

    private val _error = MutableLiveData<Any>()
    val error: LiveData<Any> = _error

    private val _remotePopularDrinks = arrayListOf<Drink>()
    private val _remoteLatestDrinks = arrayListOf<Drink>()
    private val _remoteRandomDrinks = arrayListOf<Drink>()

    //TODO refactor rest in one fun
    fun getPopularCocktails() {
        viewModelScope.launch {
            if (networkIdentifier.isNetworkConnected()) {
                when (val drinkResult = getPopularCocktailsUseCase.getPopularCocktails()) {
                    is Result.Success -> {
                        _remotePopularDrinks.clear()
                        _remotePopularDrinks.addAll(drinkResult.data)
                        _popularCocktails.value =
                            itemMapper.fromDrinkItemsToCocktailItems(_remotePopularDrinks)
                        getLatestCocktails()
                    }

                    is Result.Error -> {
                        _dataLoading.postValue(false)
                        _popularCocktails.value = emptyList()
                        _error.postValue(drinkResult.exception.message)
                    }
                }
            } else {
                _error.postValue(R.string.no_internet_connection)
            }
        }
    }

    fun getLatestCocktails() {
        viewModelScope.launch {
            when (val drinkResult = getLatestCocktailsUseCase.getLatestCocktails()) {
                is Result.Success -> {
                    _remoteLatestDrinks.clear()
                    _remoteLatestDrinks.addAll(drinkResult.data)
                    _latestCocktails.value = itemMapper.fromDrinkItemsToCocktailItems(_remoteLatestDrinks)
                    getRandomCocktails()
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    _latestCocktails.value = emptyList()
                    _error.postValue(drinkResult.exception.message)
                }
            }
        }
    }

    fun getRandomCocktails() {
        viewModelScope.launch {
            when (val drinkResult = getRandomCocktailsUseCase.getRandomCocktails()) {
                is Result.Success -> {
                    _remoteRandomDrinks.clear()
                    _remoteRandomDrinks.addAll(drinkResult.data)
                    _randomCocktails.value = itemMapper.fromDrinkItemsToCocktailItems(_remoteRandomDrinks)
                    _dataLoading.postValue(false)
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    _randomCocktails.value = emptyList()
                    _error.postValue(drinkResult.exception.message)
                }
            }
        }
    }

//    fun bookmark(book: BookWithStatus) {
//        viewModelScope.launch {
//            bookmarkBookUseCase.invoke(mapper.fromBookWithStatusToVolume(book))
//        }
//    }
//
//    fun unbookmark(book: BookWithStatus) {
//        viewModelScope.launch {
//            unbookmarkBookUseCase.invoke(mapper.fromBookWithStatusToVolume(book))
//        }
//    }
}