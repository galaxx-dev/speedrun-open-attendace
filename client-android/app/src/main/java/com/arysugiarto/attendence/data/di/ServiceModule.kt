package com.arysugiarto.attendence.data.di

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesInAppUpdateManager(context: Context) =
        AppUpdateManagerFactory.create(context)

}