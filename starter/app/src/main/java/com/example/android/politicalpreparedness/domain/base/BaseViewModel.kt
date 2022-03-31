package com.example.android.politicalpreparedness.domain.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    fun <T, Y> BaseUsecase<T, Y>.executeAndDispose(
        mutableLiveData: MutableLiveData<Resource<T>>,
        params: Y? = null
    ) {
        viewModelScope.launch {
            mutableLiveData.postValue(Resource.loading())
            Log.d("BaseViewModel", "loading")
            val scope = Dispatchers.IO
            try {
                withContext(scope) {
                    val response = this@executeAndDispose.invoke(params)
                    Log.d("BaseViewModel", "success ${response.toString()}")
                    mutableLiveData.postValue(Resource.success(response))
                    scope.cancel()
                }
            } catch (e: Exception) {
                Log.d("BaseViewModel", "error ${e.message}")

                mutableLiveData.postValue(Resource.error(e))
                scope.cancel()
            }
        }
    }
}
