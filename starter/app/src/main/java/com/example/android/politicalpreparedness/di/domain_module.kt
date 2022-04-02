package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.domain.DeleteElectionUseCase
import com.example.android.politicalpreparedness.domain.GetRepresentativeFromNetworkUseCase
import com.example.android.politicalpreparedness.domain.LoadElectionUseCase
import com.example.android.politicalpreparedness.domain.SaveElectionUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetRepresentativeFromNetworkUseCase(get()) }

    factory { LoadElectionUseCase(get()) }
    factory { DeleteElectionUseCase(get()) }
    factory { SaveElectionUseCase(get()) }
}