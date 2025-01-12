package com.example.android.politicalpreparedness.domain.base

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*

fun <T> LiveData<Resource<T>>.observe(
    lifecycleOwner: LifecycleOwner,
    observer: (Resource<T>?) -> Unit
) =
    observe(lifecycleOwner, Observer { observer(it) })

fun <T> MutableLiveData<Resource<T>>.observeWithResource(
    responseInterface: ResponseInterface,
    onError: ((Throwable) -> Unit)? = null,
    onLoading: ((isLoading: Boolean) -> Unit)? = null,
    onSuccess: (T) -> Unit
) {
    observe(responseInterface) {
        if (it != null) {
            when (it.resourceState) {
                ResourceState.LOADING -> {
                    if (onLoading != null) {
                        onLoading(true)
                    } else {
                        responseInterface.onLoading(true)
                    }
                }
                ResourceState.SUCCESS -> {
                    if (onLoading != null) {
                        onLoading(false)
                    } else {
                        responseInterface.onLoading(false)
                    }
                    onSuccess(it.data!!)
                }
                ResourceState.ERROR -> {
                    if (onLoading != null) {
                        onLoading(false)
                    } else {
                        responseInterface.onLoading(false)
                    }
                    if (onError != null) {
                        onError(it.error!!)
                    } else {
                        responseInterface.onError(it.error!!)
                    }
                }
            }
        }
    }
}
