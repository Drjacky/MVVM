package ir.hosseinabbasi.mvvm.ui.home

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ir.hosseinabbasi.data.api.AlbumApi
import ir.hosseinabbasi.data.datasource.album.AlbumsApiDataSource
import ir.hosseinabbasi.data.datasource.album.AlbumsApiDataSourceImpl
import ir.hosseinabbasi.data.datasource.album.AlbumsDatabaseDataSource
import ir.hosseinabbasi.data.datasource.album.AlbumsDatabaseDataSourceImpl
import ir.hosseinabbasi.data.db.album.AlbumDao
import ir.hosseinabbasi.data.repository.album.AlbumsRepositoryImpl
import ir.hosseinabbasi.domain.repository.album.AlbumsRepository
import ir.hosseinabbasi.domain.usecase.album.GetAlbumsUseCase
import ir.hosseinabbasi.domain.usecase.album.GetAlbumsUseCaseImpl
import ir.hosseinabbasi.mvvm.common.transformer.AsyncFTransformer
import ir.hosseinabbasi.mvvm.common.transformer.AsyncSTransformer
import ir.hosseinabbasi.mvvm.di.qualifier.ViewModelKey
import java.util.concurrent.Executors

/**
 * Created by Dr.jacky on 9/7/2018.
 */
@Module(includes = [
    HomeModule.HomeVM::class])
abstract class HomeModule {

    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @Module
    companion object {

        @JvmStatic
        @Provides
        //@PerFragment
        fun provideDatabaseSource(albumDao: AlbumDao): AlbumsDatabaseDataSource =
                AlbumsDatabaseDataSourceImpl(albumDao, Executors.newSingleThreadExecutor())

        @JvmStatic
        @Provides
        //@PerFragment
        fun provideApiSource(api: AlbumApi): AlbumsApiDataSource = AlbumsApiDataSourceImpl(api)

        @JvmStatic
        @Provides
        //@PerFragment
        fun provideRepository(
                apiSource: AlbumsApiDataSource,
                databaseSource: AlbumsDatabaseDataSource
        ): AlbumsRepository {
            return AlbumsRepositoryImpl(apiSource, databaseSource)
        }

        @JvmStatic
        @Provides
        //@PerFragment
        fun provideGetAlbumsUseCaseImpl(repository: AlbumsRepository): GetAlbumsUseCase = GetAlbumsUseCaseImpl(AsyncFTransformer(), AsyncSTransformer(), AsyncSTransformer(), repository)
    }

    @Module
    abstract class HomeVM {
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
    }
}
