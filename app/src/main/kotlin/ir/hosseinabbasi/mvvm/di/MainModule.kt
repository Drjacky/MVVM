package ir.hosseinabbasi.mvvm.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.hosseinabbasi.mvvm.di.home.HomeFragmentModule
import ir.hosseinabbasi.presentation.ui.MainActivity

/**
 * Created by Dr.jacky on 10/17/2018.
 */
@Module(includes = [HomeFragmentModule::class])
abstract class MainModule {

    //@PerActivity
    @ContributesAndroidInjector
    abstract fun get(): MainActivity
}