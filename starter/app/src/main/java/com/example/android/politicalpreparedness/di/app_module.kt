package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.presentation.election.ElectionsViewModel
import com.example.android.politicalpreparedness.presentation.election.VoterInfoViewModel
import com.example.android.politicalpreparedness.presentation.representative.RepresentativeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel{ ElectionsViewModel(get(), get())}

    viewModel{ VoterInfoViewModel(get(), get(), get(), get())}

    viewModel{ RepresentativeViewModel(get())}

}

