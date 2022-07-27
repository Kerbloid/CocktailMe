package com.example.cocktailme.presentation.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.entities.CocktailItem
import com.example.cocktailme.mappers.CocktailItemMapper
import com.example.cocktailme.presentation.base.BaseViewModel
import com.example.domain.usecases.local.GetFavoritesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val cocktailItemMapper: CocktailItemMapper
) : BaseViewModel() {

    private val _favoriteCocktails = MutableLiveData<List<CocktailItem>>()
    val favoriteCocktails = _favoriteCocktails

    fun getFavoriteCocktails() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteCocktails.postValue(
                cocktailItemMapper.fromDrinkItemsToCocktailItems(
                    getFavoritesUseCase.getFavorites()
                )
            )
            _dataLoading.postValue(false)
        }
    }
}