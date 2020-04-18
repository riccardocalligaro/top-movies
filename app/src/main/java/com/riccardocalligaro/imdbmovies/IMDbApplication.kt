package com.riccardocalligaro.imdbmovies

import android.app.Application
import com.riccardocalligaro.imdbmovies.data.DataModule.dataModule
import com.riccardocalligaro.imdbmovies.domain.DomainModule.domainModule
import com.riccardocalligaro.imdbmovies.presentation.PresentationModule.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class IMDbApplication : Application() {
    override fun onCreate() {
        super.onCreate()


        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            androidContext(this@IMDbApplication)


            modules(
                listOf(
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}