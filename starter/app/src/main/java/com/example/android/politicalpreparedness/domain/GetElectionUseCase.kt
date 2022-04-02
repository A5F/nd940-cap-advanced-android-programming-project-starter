package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.network.models.GetVotersRequest
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.domain.base.BaseUsecase
import com.example.android.politicalpreparedness.domain.base.SyncUsecase

//use case per network download
class GetElectionUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<List<Election>, Unit?>(

) {
    override suspend fun invoke(params: Unit?): List<Election> {
        return politicalRepository.getElectionList()
    }
}

