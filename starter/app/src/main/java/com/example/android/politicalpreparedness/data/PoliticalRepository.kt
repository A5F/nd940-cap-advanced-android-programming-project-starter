package com.example.android.politicalpreparedness.data

import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.network.CivicsApiService
import com.example.android.politicalpreparedness.data.network.RetrofitUtils
import com.example.android.politicalpreparedness.data.network.RetrofitUtils.checkAndParseResponse
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.data.network.models.GetVotersRequest
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.presentation.representative.model.Representative

class PoliticalRepository(private val appService: CivicsApiService, private val database: ElectionDao) {

    fun getRepresentativesFromNetworkAsync(address: Address): List<Representative> {
        val response = RetrofitUtils.execute(appService.getRepresentativesFromNetworkAsync(address.toFormattedString()))
        val parsedResponse = response.checkAndParseResponse()
        return parsedResponse.offices.flatMap { office -> office.getRepresentatives(parsedResponse.officials) }
    }

    fun getVotersInfo(votersRequest: GetVotersRequest ): VoterInfoResponse {
        val response = appService.getVoterInfoFromNetwork(votersRequest.address, votersRequest.electionId).execute()
        val parsedResponse = response.checkAndParseResponse()
        return parsedResponse

    }

}