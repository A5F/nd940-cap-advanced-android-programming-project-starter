package com.example.android.politicalpreparedness.presentation.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.network.models.Division
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    //Add live data to hold voter info
    fun getElectionData(division: Division, id: Long) {
        viewModelScope.launch {
            _state.value = State.LOADING
            kotlin.runCatching {
                val address = listOf(division.state, division.country)
                    .filterNot { it.isBlank() }
                    .joinToString(separator = ",")
                electionNetworkDataRepository.value.getVotersInfo(address, id)
            }.onSuccess {
                if (it is Result.Success) {
                    _state.value = State.SUCCESS
                    _voterInfoLiveData.value = it.data
                } else {
                    it as Result.Error
                    _state.value = State.ERROR(it.message)
                }
            }.onFailure {
                _state.value = State.ERROR("Failed to load data")
                it.printStackTrace()
            }
        }
    }
    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}