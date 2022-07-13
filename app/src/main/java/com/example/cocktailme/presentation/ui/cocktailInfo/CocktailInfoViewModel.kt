package com.example.cocktailme.presentation.ui.cocktailInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.R
import com.example.cocktailme.common.NetworkIdentifier
import com.example.cocktailme.entities.Cocktail
import com.example.cocktailme.mappers.CocktailMapper
import com.example.data.ALCOHOLIC
import com.example.data.CATEGORY
import com.example.data.GLASS
import com.example.data.IBA
import com.example.domain.common.Result
import com.example.domain.usecases.GetCocktailByIdUseCase
import kotlinx.coroutines.launch

class CocktailInfoViewModel(
    private val getCocktailByIdUseCase: GetCocktailByIdUseCase,
    private val cocktailMapper: CocktailMapper,
    private val networkIdentifier: NetworkIdentifier
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _cocktail = MutableLiveData<Cocktail?>()
    val cocktail = _cocktail

    private var rawCocktail: Cocktail? = null

    private val _error = MutableLiveData<Any>()
    val error: LiveData<Any> = _error

    fun getCocktail(id: String) {
        viewModelScope.launch {
            if (networkIdentifier.isNetworkConnected()) {
                when (val drinkResult = getCocktailByIdUseCase.getCocktailById(id)) {
                    is Result.Success -> {
                        rawCocktail =
                            cocktailMapper.fromDrinkToCocktail(drinkResult.data.first())
                        _cocktail.value = cocktailMapper.fromDrinkToCocktail(drinkResult.data.first())
                        _dataLoading.postValue(false)
                    }

                    is Result.Error -> {
                        _dataLoading.postValue(false)
                        _cocktail.value = null
                        rawCocktail = null
                        _error.postValue(drinkResult.exception.message)
                    }
                }
            } else {
                _error.postValue(R.string.no_internet_connection)
            }
        }
    }

    fun getInfo(): List<Pair<String, String?>> {
        return listOf(
            IBA to rawCocktail?.IBA,
            ALCOHOLIC to rawCocktail?.alcoholic?.lowercase(),
            CATEGORY to rawCocktail?.category?.lowercase(),
            GLASS to rawCocktail?.glass
        )
    }

    fun getIngredients(): List<Pair<Any?, Any?>> {
        return listOf(
            rawCocktail?.measure1 to rawCocktail?.ingredient1,
            rawCocktail?.measure2 to rawCocktail?.ingredient2,
            rawCocktail?.measure3 to rawCocktail?.ingredient3,
            rawCocktail?.measure4 as String? to rawCocktail?.ingredient4 as String?,
            rawCocktail?.measure5 as String? to rawCocktail?.ingredient5 as String?,
            rawCocktail?.measure6 as String? to rawCocktail?.ingredient6 as String?,
            rawCocktail?.measure7 as String? to rawCocktail?.ingredient7 as String?,
            rawCocktail?.measure8 as String? to rawCocktail?.ingredient8 as String?,
            rawCocktail?.measure9 as String? to rawCocktail?.ingredient9 as String?,
            rawCocktail?.measure10 as String? to rawCocktail?.ingredient10 as String?
        )
    }
}