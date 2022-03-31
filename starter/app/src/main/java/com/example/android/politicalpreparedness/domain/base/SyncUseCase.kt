package com.example.android.politicalpreparedness.domain.base

abstract class SyncUsecase<Y, in Params>() {
    abstract operator fun invoke(params: Params? = null):Y
}
