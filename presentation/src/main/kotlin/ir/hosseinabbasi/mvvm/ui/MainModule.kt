package ir.hosseinabbasi.mvvm.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.hosseinabbasi.mvvm.di.qualifier.PerActivity
import ir.hosseinabbasi.mvvm.ui.home.HomeModule

/**
 * Created by Dr.jacky on 10/17/2018.
 */
@Module(includes = [HomeModule::class])
abstract class MainModule {

    //@PerActivity
    @ContributesAndroidInjector
    abstract fun get(): MainActivity
}