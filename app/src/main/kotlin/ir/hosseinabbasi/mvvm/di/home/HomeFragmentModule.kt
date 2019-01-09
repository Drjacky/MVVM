package ir.hosseinabbasi.mvvm.di.home

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.hosseinabbasi.presentation.ui.home.HomeFragment

@Module
abstract class HomeFragmentModule {

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment
}