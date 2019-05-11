package com.example.myapplication.di.modules

import com.example.myapplication.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributeModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}