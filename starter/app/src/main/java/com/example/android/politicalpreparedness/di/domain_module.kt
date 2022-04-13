package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.domain.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetRepresentativeUseCase(get()) }
    factory { GetVotersInfoUseCase(get()) }
    factory { GetElectionUseCase(get()) }

    factory { LoadElectionUseCase(get()) }
    factory { DeleteElectionUseCase(get()) }
    factory { SaveElectionUseCase(get()) }
    factory { LoadElectionListUseCase(get()) }

}