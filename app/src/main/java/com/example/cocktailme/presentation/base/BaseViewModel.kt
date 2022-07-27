package com.example.cocktailme.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailme.common.SingleLiveEvent

open class BaseViewModel: ViewModel() {
    protected val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    protected val _error = SingleLiveEvent<Any>()
    val error: LiveData<Any> = _error

    protected fun <T> getDataByUseCase(result: com.example.domain.common.Result<T>): T? {
        return when (result) {
            is com.example.domain.common.Result.Error -> {
                _error.postValue(result.exception)
                null
            }
            is com.example.domain.common.Result.Success -> {
                _error.postValue("")
                result.data
            }
        }
    }
}