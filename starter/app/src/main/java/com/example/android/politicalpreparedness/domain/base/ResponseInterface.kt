package com.example.android.politicalpreparedness.domain.base

import androidx.lifecycle.LifecycleOwner

interface ResponseInterface: LifecycleOwner {

    fun onLoading(isLoading: Boolean){

    }

    fun onError(error: Throwable){

    }
}
