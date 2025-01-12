package com.example.android.politicalpreparedness.presentation.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.data.network.models.Division
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.network.models.GetVotersRequest
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.domain.DeleteElectionUseCase
import com.example.android.politicalpreparedness.domain.GetVotersInfoUseCase
import com.example.android.politicalpreparedness.domain.LoadElectionUseCase
import com.example.android.politicalpreparedness.domain.SaveElectionUseCase
import com.example.android.politicalpreparedness.domain.base.BaseViewModel
import com.example.android.politicalpreparedness.domain.base.Resource

class VoterInfoViewModel(private val votersInfoUseCase: GetVotersInfoUseCase,
                         private val loadElectionUseCase: LoadElectionUseCase,
                         private val saveElectionUseCase: SaveElectionUseCase,
                         private val deleteElectionUseCase: DeleteElectionUseCase
                         ) : BaseViewModel() {

    val electionLiveData =  MutableLiveData<Resource<Election>>()
    val saveElectionLiveData =  MutableLiveData<Resource<Boolean>>()
    val deleteElectionLiveData =  MutableLiveData<Resource<Boolean>>()


    val webUrl: LiveData<String>
        get() = _webUrlLiveData
    private val _webUrlLiveData = MutableLiveData<String>()


    //Add live data to hold voter info
    var voterInfoLiveData = MutableLiveData<Resource<VoterInfoResponse>>()


    // Add var and methods to populate voter info
    fun getElectionData(division: Division, id: Int) {
        val address = listOf(division.state, division.country)
            .filterNot { it.isBlank() }
            .joinToString(separator = ",")
        votersInfoUseCase.executeAndDispose(voterInfoLiveData,
            GetVotersRequest(address = address, electionId= id)
        )

        getElection(electionId= id) //check if i follow the election
    }


    // Add var and methods to support loading URLs
    fun loadUrl(url: String?) {
        url?.also {
            _webUrlLiveData.value =it
        }
    }


    //Add var and methods to save and remove elections to local database
    fun getElection(electionId: Int){
        loadElectionUseCase.executeAndDispose(electionLiveData, electionId)
    }
    fun updateElection(electionId: Int){
        val election = electionLiveData.value?.data as Election?
        if (election!= null) {
            Log.d("updateelection", "election not null, devo eliminare")
            deleteElectionUseCase.executeAndDispose(deleteElectionLiveData, electionId)
        }else{
            Log.d("updateelection", "election null, devo salvare")
            saveElectionUseCase.executeAndDispose(saveElectionLiveData, electionId)
        }

    }
    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}