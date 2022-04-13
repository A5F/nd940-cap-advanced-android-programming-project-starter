package com.example.android.politicalpreparedness.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse (
    val election: Election,
    val pollingLocations: String? = null, //TODO: Future Use
    val contests: String? = null, //TODO: Future Use
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null
) {
    override fun toString(): String {
        return "VoterInfoResponse(election=$election, pollingLocations=$pollingLocations, contests=$contests, state=$state, electionElectionOfficials=$electionElectionOfficials)"
    }
}
