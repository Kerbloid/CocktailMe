package com.example.cocktailme.presentation.ui.randomCocktails

import androidx.lifecycle.*
import com.example.cocktailme.presentation.entities.Cocktail
import com.example.cocktailme.presentation.mappers.CocktailMapper
import com.example.domain.entities.Drink
import com.example.domain.usecases.GetRandomCocktailsUseCase
import kotlinx.coroutines.launch
import com.example.domain.common.Result

class RandomCocktailsViewModel(
    private val getRandomCocktailsUseCase: GetRandomCocktailsUseCase,
    private val mapper: CocktailMapper
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _dataUpdating = MutableLiveData(false)
    val dataUpdating: LiveData<Boolean> = _dataUpdating

    private val _cocktails = MutableLiveData<List<Cocktail>>()
    val cocktails = _cocktails

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _remoteDrinks = arrayListOf<Drink>()

    fun getRandomCocktails() {
        viewModelScope.launch {
            when (val drinkResult = getRandomCocktailsUseCase.getRandomCocktails()) {
                is Result.Success -> {
                    _remoteDrinks.clear()
                    _remoteDrinks.addAll(drinkResult.data)
                    cocktails.value = mapper.fromDrinkToCocktail(_remoteDrinks)
                    _dataLoading.postValue(false)
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    cocktails.value = emptyList()
                    _error.postValue(drinkResult.exception.message)
                }
            }
        }
    }

    fun updateRandomCocktails() {
        viewModelScope.launch {
            _dataUpdating.postValue(true)
            when (val drinkResult = getRandomCocktailsUseCase.getRandomCocktails()) {
                is Result.Success -> {
                    _remoteDrinks.clear()
                    _remoteDrinks.addAll(drinkResult.data)
                    cocktails.value = mapper.fromDrinkToCocktail(_remoteDrinks)
                    _dataUpdating.postValue(false)
                }

                is Result.Error -> {
                    _dataUpdating.postValue(false)
                    cocktails.value = emptyList()
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