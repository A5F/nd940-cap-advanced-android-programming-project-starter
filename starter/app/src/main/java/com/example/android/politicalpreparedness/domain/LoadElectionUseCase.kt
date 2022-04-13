package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.network.models.GetVotersRequest
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.domain.base.BaseUsecase
import com.example.android.politicalpreparedness.domain.base.SyncUsecase
import java.lang.Exception

//use case per local load

class LoadElectionUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<Election, Int?>(

) {
    override suspend fun invoke(params: Int?): Election {
        val election =  politicalRepository.getElectionById(params!!)
        if (election != null){
            return election
        }else{
            throw Exception("ELECTION_NOT_ALREADY_SAVED")
        }
    }
}

