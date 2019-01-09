package ir.hosseinabbasi.mvvm.di.home

import dagger.Module
import dagger.Provides
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
import ir.hosseinabbasi.presentation.common.transformer.AsyncFTransformer
import ir.hosseinabbasi.presentation.common.transformer.AsyncSTransformer
import java.util.concurrent.Executors

/**
 * Created by Dr.jacky on 9/7/2018.
 */
@Module
class HomeModule {

    @Provides
    //@PerFragment
    fun provideDatabaseSource(albumDao: AlbumDao): AlbumsDatabaseDataSource =
            AlbumsDatabaseDataSourceImpl(albumDao, Executors.newSingleThreadExecutor())

    @Provides
    //@PerFragment
    fun provideApiSource(api: AlbumApi): AlbumsApiDataSource = AlbumsApiDataSourceImpl(api)

    @Provides
    //@PerFragment
    fun provideRepository(
            apiSource: AlbumsApiDataSource,
            databaseSource: AlbumsDatabaseDataSource
    ): AlbumsRepository {
        return AlbumsRepositoryImpl(apiSource, databaseSource)
    }

    @Provides
    //@PerFragment
    fun provideGetAlbumsUseCaseImpl(repository: AlbumsRepository): GetAlbumsUseCase = GetAlbumsUseCaseImpl(AsyncFTransformer(), AsyncSTransformer(), AsyncSTransformer(), repository)
}
