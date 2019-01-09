package ir.hosseinabbasi.mvvm

import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ir.hosseinabbasi.mvvm.di.DaggerAppComponent

/**
 * Created by Dr.jacky on 9/7/2018.
 */
class MyApplication : DaggerApplication() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerAppComponent.builder().create(this)
    }
}