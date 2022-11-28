package com.albocal.exoplayerapp

import com.albocal.exoplayerapp.di.applyAutoInjector
import com.albocal.exoplayerapp.di.components.DaggerAppComponent
import com.albocal.exoplayerapp.di.modules.AppModule
import dagger.android.DaggerApplication


class ExoPlayerApp : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        applyAutoInjector()
    }

    override fun applicationInjector() =
        DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .build()

}