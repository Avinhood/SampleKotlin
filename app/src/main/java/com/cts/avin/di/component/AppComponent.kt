package com.cts.avin.di.component

import android.app.Application

import com.cts.avin.base.BaseApplication
import com.cts.avin.di.modules.AppModule
import com.cts.avin.di.modules.FeatureModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication


@Component(modules = [AppModule::class, AndroidSupportInjectionModule::class, FeatureModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
