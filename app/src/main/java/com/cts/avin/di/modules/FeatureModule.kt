package com.cts.avin.di.modules

import android.app.Application
import android.content.Context

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cts.avin.di.util.ViewModelKey
import com.cts.avin.ui.main.HomeActivity
import com.cts.avin.util.ViewModelFactory
import com.cts.avin.viewmodel.AboutListViewModel

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FeatureModule {
    @Binds
    @IntoMap
    @ViewModelKey(AboutListViewModel::class)
    abstract fun bindMainListViewModel(mainViewModel: AboutListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun contributeActivityInjector(): HomeActivity

    @Binds
    abstract fun provideContext(application: Application): Context

}
