package com.example.android.politicalpreparedness.domain

import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.domain.base.BaseUsecase
import com.example.android.politicalpreparedness.presentation.representative.model.Representative

class GetRepresentativeFromNetworkUseCase(
    private val politicalRepository: PoliticalRepository
)  : BaseUsecase<List<Representative>, Address?>(

) {
    override suspend fun invoke(params: Address?): List<Representative> {
        return politicalRepository.getRepresentativesFromNetworkAsync(params!!)
    }
}

