package com.example.android.politicalpreparedness.presentation.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.data.database.ElectionDao
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
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val votersInfoUseCase: GetVotersInfoUseCase,
                        private val loadElectionUseCase: LoadElectionUseCase,
                         private val saveElectionUseCase: SaveElectionUseCase,
                         private val deleteElectionUseCase: DeleteElectionUseCase)
    : BaseViewModel() {

    val statusElectionLiveData =  MutableLiveData<Resource<Election?>>()

    val electionLiveData =  MutableLiveData<Resource<Election?>>()
    val saveElectionLiveData =  MutableLiveData<Resource<Boolean>>()
    val deleteElectionLiveData =  MutableLiveData<Resource<Boolean>>()


    val webUrl: LiveData<String>
        get() = _webUrlLiveData
    private val _webUrlLiveData = MutableLiveData<String>()


    //Add live data to hold voter info
    private var _voterInfoLiveData = MutableLiveData<Resource<VoterInfoResponse>>()

    // Add var and methods to populate voter info
    fun getElectionData(division: Division, id: Long) {
        val address = listOf(division.state, division.country)
            .filterNot { it.isBlank() }
            .joinToString(separator = ",")
        votersInfoUseCase.executeAndDispose(_voterInfoLiveData,
            GetVotersRequest(address = address, electionId= id)
        )
    }


    // Add var and methods to support loading URLs
    fun loadUrl(url: String) {
        _webUrlLiveData.value = url
    }


    //Add var and methods to save and remove elections to local database
    fun getElection(electionId: Long){
        loadElectionUseCase.executeAndDispose(electionLiveData, electionId)
    }

    fun saveElection(electionId: Long){
        saveElectionUseCase.executeAndDispose(saveElectionLiveData, electionId)
    }

    fun deleteElection(electionId: Long){
        deleteElectionUseCase.executeAndDispose(deleteElectionLiveData, electionId)
    }


    // cont'd -- Populate initial state of save button to reflect proper action based on election saved status
    fun onPageUpdate(electionId: Long){
        loadElectionUseCase.executeAndDispose(statusElectionLiveData, electionId)
    }
    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}