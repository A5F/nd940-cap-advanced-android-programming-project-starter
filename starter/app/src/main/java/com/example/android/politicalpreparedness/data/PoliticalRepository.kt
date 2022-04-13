package com.example.android.politicalpreparedness.data

import com.example.android.politicalpreparedness.data.database.ElectionDao
import com.example.android.politicalpreparedness.data.network.CivicsApiService
import com.example.android.politicalpreparedness.data.network.RetrofitUtils
import com.example.android.politicalpreparedness.data.network.RetrofitUtils.checkAndParseResponse
import com.example.android.politicalpreparedness.data.network.models.Address
import com.example.android.politicalpreparedness.data.network.models.Election
import com.example.android.politicalpreparedness.data.network.models.GetVotersRequest
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.presentation.representative.model.Representative

class PoliticalRepository(private val appService: CivicsApiService, private val database: ElectionDao) {

    var currentCompletElection : List<Election>? = null

    fun getRepresentatives(address: Address): List<Representative> {
        val response = RetrofitUtils.execute(appService.getRepresentatives(address.toFormattedString()))
        val parsedResponse = response.checkAndParseResponse()
        return parsedResponse.offices.flatMap { office -> office.getRepresentatives(parsedResponse.officials) }
    }

    fun getVotersInfo(votersRequest: GetVotersRequest ): VoterInfoResponse {
        val response = appService.getVoterInfo(votersRequest.address, votersRequest.electionId).execute()
        val parsedResponse = response.checkAndParseResponse()
        return parsedResponse

    }

    fun getElectionList(): List<Election>{
        val response = RetrofitUtils.execute(appService.getElections())
        val parsedResponse = response.checkAndParseResponse()
        currentCompletElection = parsedResponse.elections
        return parsedResponse.elections
    }


    fun loadElectionList(): List<Election>{
        return database.getListOfElections()?: emptyList()
    }

    fun getElectionById(electionId: Int): Election?{
        return database.getElectionById(electionId)
    }

    fun saveElection(election: Election):Boolean{
        database.saveElection(election)
        return true
    }

    fun deleteElection(electionId: Int):Boolean{
        database.removeElection(electionId)
        return true
    }

}