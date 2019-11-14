package com.cts.avin.di.modules

import com.cts.avin.ui.main.AboutListFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun provideMainFragment(): AboutListFragment
}
