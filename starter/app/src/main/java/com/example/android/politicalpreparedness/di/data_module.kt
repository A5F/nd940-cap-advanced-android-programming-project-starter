package com.example.android.politicalpreparedness.di

import androidx.room.Room
import com.example.android.politicalpreparedness.data.PoliticalRepository
import com.example.android.politicalpreparedness.data.database.ElectionDatabase
import com.example.android.politicalpreparedness.data.network.CivicsApiService
import com.example.android.politicalpreparedness.data.network.jsonadapter.DateAdapter
import com.example.android.politicalpreparedness.data.network.jsonadapter.ElectionAdapter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val API_KEY = "" //TODO: Place your API Key Here

private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

const val OKHTTP_CLIENT = "okhttp_client"

val dataModule = module {

   //repository
    single { PoliticalRepository(get(), get()) }

    //database
    single {
        synchronized(this) {
                Room.databaseBuilder(
                    androidContext().applicationContext,
                    ElectionDatabase::class.java,
                    "election_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
    }


    ///network
//  Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
    single {
        // private val moshi
         Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
             .add(ElectionAdapter())
             .add(DateAdapter())
            .build()
    }
    single {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get(named(OKHTTP_CLIENT)))
            .baseUrl(BASE_URL)
            .build().create(CivicsApiService::class.java)

    }

    single(named(OKHTTP_CLIENT)) {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = original
                    .newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

}


