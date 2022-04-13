package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.domain.base.BaseUsecase

//use case per local load

class LoadElectionListUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<List<Election> , Unit?>(

) {
    override suspend fun invoke(params: Unit?): List<Election> {
        return politicalRepository.loadElectionList()
    }
}

