package com.example.android.politicalpreparedness.data.network.models

import androidx.room.*
import com.squareup.moshi.*
import java.io.Serializable
import java.util.*

@Entity(tableName = "election_table")
data class Election(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "name")val name: String,
        @ColumnInfo(name = "electionDay")val electionDay: Date,
        @Embedded(prefix = "ocd-division/") @Json(name="ocdDivisionId") val division: Division
): Serializable