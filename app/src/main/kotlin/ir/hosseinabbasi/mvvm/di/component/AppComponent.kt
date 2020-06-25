package ir.hosseinabbasi.mvvm.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ir.hosseinabbasi.mvvm.MyApplication
import ir.hosseinabbasi.mvvm.di.AppModule
import ir.hosseinabbasi.mvvm.di.MainModule
import ir.hosseinabbasi.mvvm.di.ViewModelModule
import ir.hosseinabbasi.mvvm.di.home.HomeModule
import javax.inject.Singleton

/**
 * Created by Dr.jacky on 9/7/2018.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelModule::class,
    AppModule::class,
    MainModule::class,
    HomeModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>()
}