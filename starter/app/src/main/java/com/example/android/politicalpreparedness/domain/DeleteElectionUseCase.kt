package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.domain.base.BaseUsecase

class DeleteElectionUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<Boolean, Int?>(

) {
    override suspend fun invoke(params: Int?): Boolean {
        return politicalRepository.deleteElection(params!!)
    }
}

