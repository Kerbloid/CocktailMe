package com.example.cocktailme.presentation.ui.cocktailInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.R
import com.example.cocktailme.common.NetworkIdentifier
import com.example.cocktailme.entities.Cocktail
import com.example.cocktailme.mappers.CocktailMapper
import com.example.domain.common.Result
import com.example.domain.usecases.GetCocktailByIdUseCase
import kotlinx.coroutines.launch

class CocktailInfoViewModel(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    private val cocktailMapper: CocktailMapper,
    private val networkIdentifier: NetworkIdentifier
): ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _cocktail = MutableLiveData<Cocktail?>()
    val cocktail = _cocktail

    private val _error = MutableLiveData<Any>()
    val error: LiveData<Any> = _error

    fun getCocktail(id: String) {
        viewModelScope.launch {
            if (networkIdentifier.isNetworkConnected()) {
                when (val drinkResult = getCocktailByIdUseCase.getCocktailById(id)) {
                    is Result.Success -> {
                        _cocktail.value =
                            cocktailMapper.fromDrinkToCocktail(drinkResult.data.first())
                        _dataLoading.postValue(false)
                    }

                    is Result.Error -> {
                        _dataLoading.postValue(false)
                        _cocktail.value = null
                        _error.postValue(drinkResult.exception.message)
                    }
                }
            } else {
                _error.postValue(R.string.no_internet_connection)
            }
        }
    }
}