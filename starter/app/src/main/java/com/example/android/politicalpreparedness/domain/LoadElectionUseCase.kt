package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.network.models.GetVotersRequest
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.domain.base.BaseUsecase
import com.example.android.politicalpreparedness.domain.base.SyncUsecase

class LoadElectionUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<Election?, Long?>(

) {
    override suspend fun invoke(params: Long?): Election? {
        return politicalRepository.getElectionById(params!!)
    }
}

