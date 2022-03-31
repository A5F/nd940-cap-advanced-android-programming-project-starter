package com.example.android.politicalpreparedness.network.jsonadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    @FromJson
    fun dateFromJson (dateString: String): Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.parse(dateString)!!
    }

    @ToJson
    fun dateToJson(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}