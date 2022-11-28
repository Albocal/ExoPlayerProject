package com.albocal.exoplayerapp.base

import com.albocal.exoplayerapp.di.Injectable
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerAppCompatActivity

open class BaseActivity : DaggerAppCompatActivity(), Injectable, HasAndroidInjector {
}