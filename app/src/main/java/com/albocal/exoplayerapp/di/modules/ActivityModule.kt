package com.albocal.exoplayerapp.di.modules

import com.albocal.exoplayerapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeRepositoriesActivity(): MainActivity

}