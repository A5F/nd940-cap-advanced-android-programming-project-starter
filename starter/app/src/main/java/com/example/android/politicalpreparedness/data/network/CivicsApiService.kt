package com.example.android.politicalpreparedness.data.network

import com.example.android.politicalpreparedness.data.network.models.ElectionResponse
import com.example.android.politicalpreparedness.data.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.data.network.models.VoterInfoResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CivicsApiService {
    // Add elections API Call
    @GET("/civicinfo/v2/elections")
    fun getElections(): Call<ElectionResponse>

    // Add voterinfo API Call
    @GET("/civicinfo/v2/voterinfo")
    fun getVoterInfo(@Query("address") address: String, @Query("electionId") electionId: Int?): Call<VoterInfoResponse>

    // Add representatives API Call
    @GET("/civicinfo/v2/representatives")
    fun getRepresentatives(@Query("address") address: String): Call<RepresentativeResponse>
}
