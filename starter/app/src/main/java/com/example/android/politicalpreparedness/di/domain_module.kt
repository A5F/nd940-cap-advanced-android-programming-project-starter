package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.domain.GetRepresentativeFromNetworkUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetRepresentativeFromNetworkUseCase(get()) }
}