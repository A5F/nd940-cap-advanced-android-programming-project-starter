package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)

interface CivicsApiService {
    // Add elections API Call
    @GET("/civicinfo/v2/elections")
    suspend fun getElectionsFromNetwork(): Response<ElectionResponse>

    // Add voterinfo API Call
    @GET("/civicinfo/v2/voterinfo")
    suspend fun getVoterInfoFromNetwork(@Query("address") address: String, @Query("electionId") electionId: Long?): Response<VoterInfoResponse>

    // Add representatives API Call
    @GET("/civicinfo/v2/representatives")
    fun getRepresentativesFromNetworkAsync(@Query("address") address: String): Deferred<RepresentativeResponse>
}
