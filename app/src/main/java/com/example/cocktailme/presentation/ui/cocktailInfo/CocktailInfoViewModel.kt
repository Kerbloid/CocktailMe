package com.example.cocktailme.presentation.ui.cocktailInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.presentation.entities.Cocktail
import com.example.cocktailme.presentation.entities.CocktailItem
import com.example.cocktailme.presentation.mappers.CocktailMapper
import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.usecases.GetCocktailByIdUseCase
import kotlinx.coroutines.launch

class CocktailInfoViewModel(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    private val cocktailMapper: CocktailMapper
): ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _cocktail = MutableLiveData<Cocktail>()
    val cocktail = _cocktail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCocktail(id: String) {
        viewModelScope.launch {
            when (val drinkResult = getCocktailByIdUseCase.getCocktailById(id)) {
                is Result.Success -> {
                    cocktail.value = cocktailMapper.fromDrinkToCocktail(drinkResult.data.first())
                    _dataLoading.postValue(false)
                }

                is Result.Error -> {
                    _dataLoading.postValue(false)
                    cocktail.value = null
                    _error.postValue(drinkResult.exception.message)
                }
            }
        }
    }
}