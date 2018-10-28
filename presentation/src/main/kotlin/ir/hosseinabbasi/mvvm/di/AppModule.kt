package ir.hosseinabbasi.mvvm.di

import android.content.Context
import android.content.res.Resources
import dagger.Binds
import dagger.Module
import dagger.Provides
import ir.hosseinabbasi.mvvm.MyApplication
import ir.hosseinabbasi.mvvm.di.qualifier.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Dr.jacky on 9/7/2018.
 */
@Module(includes = [NetModule::class, DbModule::class])
abstract class AppModule {

    @ApplicationContext
    @Binds
    abstract fun provideApplicationContext(myApplication: MyApplication): Context

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideAppResources(context: Context): Resources {
            return context.resources
        }
    }
}