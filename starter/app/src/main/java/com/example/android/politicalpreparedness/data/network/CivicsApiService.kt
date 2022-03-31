package com.example.android.politicalpreparedness.data.network

import com.example.android.politicalpreparedness.data.network.models.ElectionResponse
import com.example.android.politicalpreparedness.data.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


// TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)

interface CivicsApiService {
    // Add elections API Call
    @GET("/civicinfo/v2/elections")
    suspend fun getElectionsFromNetwork(): Call<ElectionResponse>

    // Add voterinfo API Call
    @GET("/civicinfo/v2/voterinfo")
    fun getVoterInfoFromNetwork(@Query("address") address: String, @Query("electionId") electionId: Long?): Call<VoterInfoResponse>

    // Add representatives API Call
    @GET("/civicinfo/v2/representatives")
    fun getRepresentativesFromNetworkAsync(@Query("address") address: String): Call<RepresentativeResponse>
}
