package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.di.appModule
import com.example.android.politicalpreparedness.di.dataModule
import com.example.android.politicalpreparedness.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PoliticalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            //  androidLogger()
            androidContext(this@PoliticalApplication)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}