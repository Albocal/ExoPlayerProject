package com.albocal.exoplayerapp.di.components

import com.albocal.exoplayerapp.ExoPlayerApp
import com.albocal.exoplayerapp.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<ExoPlayerApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: ExoPlayerApp): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: ExoPlayerApp)
}