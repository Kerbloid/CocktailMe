package com.example.cocktailme.presentation.ui.cocktailInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailme.R
import com.example.cocktailme.common.NetworkIdentifier
import com.example.cocktailme.entities.Cocktail
import com.example.cocktailme.mappers.CocktailMapper
import com.example.cocktailme.presentation.base.BaseViewModel
import com.example.data.*
import com.example.data.mappers.DrinkLocalToDrinkMapper
import com.example.domain.common.Result
import com.example.domain.entities.Drink
import com.example.domain.usecases.local.GetDrinkByIdLocalUseCase
import com.example.domain.usecases.local.RemoveFromFavoriteUseCase
import com.example.domain.usecases.local.SaveToFavoriteUseCase
import com.example.domain.usecases.remote.GetCocktailByIdRemoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CocktailInfoViewModel(
    private val getCocktailByIdRemoteUseCase: GetCocktailByIdRemoteUseCase,
    private val saveToFavoriteUseCase: SaveToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val getDrinkByIdLocalUseCase: GetDrinkByIdLocalUseCase,
    private val cocktailMapper: CocktailMapper,
    private val drinkLocalToDrinkMapper: DrinkLocalToDrinkMapper,
    private val networkIdentifier: NetworkIdentifier
) : BaseViewModel() {

    private val _cocktail = MutableLiveData<Cocktail?>()
    val cocktail = _cocktail

    private var rawDrink: Drink? = null

    private val _isInFavorite = MutableLiveData<Boolean>()
    val isInFavorite = _isInFavorite

    fun getCocktail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val localDrink = getDrinkByIdLocalUseCase.getDrinkById(id)
            if (localDrink != null) {
                rawDrink = localDrink
                _cocktail.postValue(cocktailMapper.fromDrinkToCocktail(localDrink))
                _isInFavorite.postValue(true)
                _dataLoading.postValue(false)
            } else {
                if (networkIdentifier.isNetworkConnected()) {
                    when (val drinkResult = getCocktailByIdRemoteUseCase.getCocktailById(id)) {
                        is Result.Success -> {
                            rawDrink = drinkResult.data.first()
                            checkIfInFavorite(rawDrink)
                            _cocktail.postValue(cocktailMapper.fromDrinkToCocktail(drinkResult.data.first()))
                            _dataLoading.postValue(false)
                        }

                        is Result.Error -> {
                            _dataLoading.postValue(false)
                            _cocktail.postValue(null)
                            rawDrink = null
                            _error.postValue(drinkResult.exception.message)
                        }
                    }
                } else {
                    _error.postValue(R.string.no_internet_connection)
                }
            }
        }
    }

    private suspend fun saveToFavorite() {
        rawDrink?.let { saveToFavoriteUseCase.saveToFavorite(it) }
    }

    private suspend fun removeFromFavorite(id: String) {
        removeFromFavoriteUseCase.removeFromFavorite(id)
    }

    fun saveOrRemoveFromFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_isInFavorite.value != true) {
                saveToFavorite()
                _isInFavorite.postValue(true)
            } else {
                rawDrink?.let { removeFromFavorite(it.id) }
                _isInFavorite.postValue(false)
            }
        }
    }

    fun getInfo(): List<Pair<String, String?>> {
        return listOf(
            NAME to rawDrink?.name,
            IBA to rawDrink?.IBA,
            ALCOHOLIC to rawDrink?.alcoholic?.lowercase(),
            CATEGORY to rawDrink?.category?.lowercase(),
            GLASS to rawDrink?.glass
        )
    }

    fun getIngredients(): List<Pair<Any?, Any?>> {
        return listOf(
            rawDrink?.measure1 to rawDrink?.ingredient1,
            rawDrink?.measure2 to rawDrink?.ingredient2,
            rawDrink?.measure3 to rawDrink?.ingredient3,
            rawDrink?.measure4 as String? to rawDrink?.ingredient4 as String?,
            rawDrink?.measure5 as String? to rawDrink?.ingredient5 as String?,
            rawDrink?.measure6 as String? to rawDrink?.ingredient6 as String?,
            rawDrink?.measure7 as String? to rawDrink?.ingredient7 as String?,
            rawDrink?.measure8 as String? to rawDrink?.ingredient8 as String?,
            rawDrink?.measure9 as String? to rawDrink?.ingredient9 as String?,
            rawDrink?.measure10 as String? to rawDrink?.ingredient10 as String?
        )
    }

    private suspend fun checkIfInFavorite(drink: Drink?) {
        drink?.let { _isInFavorite.postValue(getDrinkByIdLocalUseCase.getDrinkById(it.id) != null) }
    }
}