package com.riccardocalligaro.imdbmovies.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.riccardocalligaro.imdbmovies.data.local.AppDatabase
import com.riccardocalligaro.imdbmovies.data.remote.IMDbService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object DataModule {

    private const val PREFERENCES_FILE_KEY = "com.riccardocalligaro.settings_preferences"


    val dataModule = module {
        // api service
        single { provideIMDbService() }
        // database
        single { provideAppDatabase(get()) }
        // shared preferences
        single { provideSettingsPreferences(androidApplication()) }

        single { get<AppDatabase>().movieDao() }

    }

    private fun provideSettingsPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)


    private fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "imdbmovies.db").build()
    }


    private fun provideIMDbService(): IMDbService {

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/riccardocalligaro/top-movies/master/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(IMDbService::class.java)
    }
}

