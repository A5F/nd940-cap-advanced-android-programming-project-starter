package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.domain.base.BaseUsecase

class SaveElectionUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<Boolean, Long?>(

) {
    override suspend fun invoke(params: Long?): Boolean {
        val election =  politicalRepository.getElectionById(params!!)

        return if (election!= null){
            politicalRepository.saveElection(election)
        }else{
            false
        }
    }
}

