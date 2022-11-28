package com.albocal.exoplayerapp.di.modules

import android.app.Application
import android.content.Context
import com.albocal.exoplayerapp.ExoPlayerApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [
    ActivityModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    UseCaseModule::class,
    ServiceModule::class
])
class AppModule(val app: ExoPlayerApp) {

    @Provides
    @Singleton
    fun providesApplication(): Application {
        return app
    }

    @Provides
    fun provideApplication(): ExoPlayerApp = app

    @Provides
    fun providesApplicationContext(): Context = app.applicationContext

}