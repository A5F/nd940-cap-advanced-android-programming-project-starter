package com.example.android.politicalpreparedness.presentation.election

import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.domain.GetElectionUseCase
import com.example.android.politicalpreparedness.domain.LoadElectionListUseCase
import com.example.android.politicalpreparedness.domain.base.BaseViewModel
import com.example.android.politicalpreparedness.domain.base.Resource

// Construct ViewModel and provide election datasource //aggiunta logica di usecase tramite koin per mantenere clean architecture
class ElectionsViewModel(
    private val getElectionUseCase: GetElectionUseCase, //fromNetwork
    private val loadElectionListUseCase: LoadElectionListUseCase //from db
): BaseViewModel() {

    //Create live data val for upcoming elections
    val electionsLiveData = MutableLiveData<Resource<List<Election>>>()


    // Create live data val for saved elections
    val savedElectionsLiveData = MutableLiveData<Resource<List<Election>>>()

    // Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    fun getElectionData(){
        getElectionUseCase.executeAndDispose(electionsLiveData)
    }

    // Create functions to navigate to saved or upcoming election voter info

    fun  loadElectionListUseCase(){
        loadElectionListUseCase.executeAndDispose(savedElectionsLiveData)
    }
}