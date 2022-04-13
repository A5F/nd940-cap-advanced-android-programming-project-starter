package com.example.android.politicalpreparedness.data.network.models

data class GetVotersRequest (
    val address: String,
    val electionId: Int?
)