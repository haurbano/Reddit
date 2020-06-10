package com.haurbano.reddit

import android.app.Application
import com.haurbano.data.di.dataModule
import com.haurbano.domain.di.domainModule
import com.haurbano.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RedditApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RedditApplication)
            modules(domainModule, dataModule, presentationModule)
        }
    }
}