package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.domain.base.BaseUsecase

class SaveElectionUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<Boolean, Int?>(

) {
    override suspend fun invoke(params: Int?): Boolean {

        val elections =  politicalRepository.currentCompletElection

        var singleElection: Election? =null
        elections?.forEach{
            if (it.id == params!!){
                singleElection= it
            }
        }

        return if (singleElection!= null){
            politicalRepository.saveElection(singleElection!!)
        }else{
            false
        }
    }
}

