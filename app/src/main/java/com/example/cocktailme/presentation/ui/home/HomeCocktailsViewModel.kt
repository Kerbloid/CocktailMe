package com.example.cocktailme.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.R
import com.example.cocktailme.common.NetworkIdentifier
import com.example.cocktailme.entities.CocktailItem
import com.example.cocktailme.mappers.CocktailItemMapper
import com.example.cocktailme.presentation.base.BaseViewModel
import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.usecases.remote.GetLatestCocktailsUseCase
import com.example.domain.usecases.remote.GetPopularCocktailsUseCase
import com.example.domain.usecases.remote.GetRandomCocktailsUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeCocktailsViewModel(
    private val getPopularCocktailsUseCase: GetPopularCocktailsUseCase,
    private val getLatestCocktailsUseCase: GetLatestCocktailsUseCase,
    private val getRandomCocktailsUseCase: GetRandomCocktailsUseCase,
    private val itemMapper: CocktailItemMapper,
    private val networkIdentifier: NetworkIdentifier
) : BaseViewModel() {

    private val _popularCocktails = MutableLiveData<List<CocktailItem>>()
    val popularCocktails = _popularCocktails
    private val _latestCocktails = MutableLiveData<List<CocktailItem>>()
    val latestCocktails = _latestCocktails
    private val _randomCocktails = MutableLiveData<List<CocktailItem>>()
    val randomCocktails = _randomCocktails

    private val _remotePopularDrinks = arrayListOf<Drink>()
    private val _remoteLatestDrinks = arrayListOf<Drink>()
    private val _remoteRandomDrinks = arrayListOf<Drink>()

    fun getCocktails() {
        viewModelScope.launch {
            if (networkIdentifier.isNetworkConnected()) {
                getCocktailsAsync()
                _dataLoading.postValue(false)
            } else {
                _error.postValue(R.string.no_internet_connection)
            }
        }
    }

    private suspend fun getCocktailsAsync() {
        coroutineScope {
            launch {
                getResult(
                    getPopularCocktailsUseCase.getPopularCocktails(),
                    _remotePopularDrinks,
                    _popularCocktails
                )
            }
            launch {
                getResult(
                    getLatestCocktailsUseCase.getLatestCocktails(),
                    _remoteLatestDrinks,
                    _latestCocktails
                )
            }
            launch {
                getResult(
                    getRandomCocktailsUseCase.getRandomCocktails(),
                    _remoteRandomDrinks,
                    _randomCocktails
                )
            }
        }
    }

    private fun getResult(
        result: Result<List<Drink>>,
        remoteList: ArrayList<Drink>,
        mutableLiveDataList: MutableLiveData<List<CocktailItem>>
    ) {
        when (result) {
            is Result.Success -> {
                remoteList.clear()
                remoteList.addAll(result.data)
                mutableLiveDataList.value = itemMapper.fromDrinkItemsToCocktailItems(remoteList)
            }
            is Result.Error -> {
                mutableLiveDataList.value = emptyList()
                _error.postValue(result.exception.message)
            }
        }
    }
}