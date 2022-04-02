package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.domain.base.BaseUsecase

class DeleteElectionUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<Boolean, Long?>(

) {
    override suspend fun invoke(params: Long?): Boolean {
        return politicalRepository.deleteElection(params!!)
    }
}

