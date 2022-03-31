package com.example.android.politicalpreparedness.domain.base

abstract class BaseUsecase<Y, in Params>() {
    abstract suspend operator fun invoke(params: Params? = null):Y
}

