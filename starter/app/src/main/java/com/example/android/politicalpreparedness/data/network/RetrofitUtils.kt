package com.example.android.politicalpreparedness.data.network

import retrofit2.Call
import retrofit2.Response

object RetrofitUtils {

        fun <T> execute(call: Call<T>): Response<T> {
            return executeCall(call)
        }

        private fun <T> executeCall(call: Call<T>): Response<T> {
            try {
                val response = call.execute()
                return response

            } catch (e: Exception) {
               throw Exception(e.message ?: e.toString())
            }
        }


        fun <T> Response<T>.checkAndParseResponse(): T {
            if (this.isSuccessful && this.body() != null) {
                val response = this.body()
                if (response != null) {
                    return response
                } else {
                    throw Exception("Network error [${this.code()} : ${this.errorBody()}")
                }
            } else {
                throw Exception("Network error [${this.code()} : ${this.errorBody()}")
            }
        }

    }