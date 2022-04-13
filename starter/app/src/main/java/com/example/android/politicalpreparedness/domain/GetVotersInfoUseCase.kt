package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.network.models.GetVotersRequest
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.domain.base.BaseUsecase

class GetVotersInfoUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<VoterInfoResponse, GetVotersRequest?>(

) {
    override suspend fun invoke(params: GetVotersRequest?): VoterInfoResponse {
        return politicalRepository.getVotersInfo(params!!)
    }
}

