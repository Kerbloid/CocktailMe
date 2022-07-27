package com.example.cocktailme.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.R
import com.example.cocktailme.common.NetworkIdentifier
import com.example.cocktailme.entities.CocktailItem
import com.example.cocktailme.mappers.CocktailItemMapper
import com.example.cocktailme.presentation.base.BaseViewModel
import com.example.domain.common.Result
import com.example.domain.usecases.remote.SearchCocktailByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchCocktailByNameUseCase: SearchCocktailByNameUseCase,
    private val cocktailItemMapper: CocktailItemMapper,
    private val networkIdentifier: NetworkIdentifier
) : BaseViewModel() {

    init {
        _dataLoading.value = false
    }

    private val _searchCocktails = MutableLiveData<List<CocktailItem>>()
    val searchCocktails = _searchCocktails

    fun searchCocktail(query: String?) {
        if (query.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            if (networkIdentifier.isNetworkConnected()) {
                when (val searchResult = searchCocktailByNameUseCase.getCocktailByName(query)) {
                    is Result.Success -> {
                        _searchCocktails.postValue(cocktailItemMapper.fromDrinkItemsToCocktailItems(searchResult.data))
                        _dataLoading.postValue(false)
                    }
                    is Result.Error -> {
                        _dataLoading.postValue(false)
                        _error.postValue(searchResult.exception.message)
                    }
                }
            } else {
                _error.postValue(R.string.no_internet_connection)
            }
        }
    }
}