package com.example.android.politicalpreparedness.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.data.network.models.Election

@Dao
interface ElectionDao {

    // Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveElection(singleElection: Election)

    // Add select all election query
    @Query("select * from election_table")
    fun getListOfElections(): List<Election>?

    //Add select single election query
    @Query("select * from election_table where id = :id")
    fun getElectionById(id: Int): Election?

    // Add delete query
    @Query("delete from election_table where id = :id")
    fun removeElection(id: Int)

    //Add clear query
    @Query("delete from election_table")
    fun nukeTable()
}