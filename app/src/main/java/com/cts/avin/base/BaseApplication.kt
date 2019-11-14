package com.cts.avin.base

import android.app.Application
import android.app.Activity
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import com.bumptech.glide.Glide
import com.cts.avin.di.component.AppComponent
import com.cts.avin.di.component.DaggerAppComponent

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }
}
