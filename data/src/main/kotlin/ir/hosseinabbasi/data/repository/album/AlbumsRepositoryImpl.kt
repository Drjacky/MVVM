package ir.hosseinabbasi.data.repository.album

import io.reactivex.Single
import ir.hosseinabbasi.data.datasource.album.AlbumsApiDataSource
import ir.hosseinabbasi.data.datasource.album.AlbumsDatabaseDataSource
import ir.hosseinabbasi.data.repository.BaseRepositoryImpl
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.entity.Entity
import ir.hosseinabbasi.domain.repository.album.AlbumsRepository

/**
 * Created by Dr.jacky on 9/28/2018.
 */
/**
 * Album repository implementation
 */
class AlbumsRepositoryImpl(
        private val apiSource: AlbumsApiDataSource,
        private val databaseSource: AlbumsDatabaseDataSource
) : BaseRepositoryImpl<Entity.Album>(), AlbumsRepository {

    /**
     * Perform implementation
     */
//    override fun getAlbums(pageSize: Int): Flowable<ResultState<PagedList<Entity.Album>>> =
//            performList(databaseSource.getAlbums(pageSize),
//                    apiSource.getAlbums(pageSize),
//                    persist = databaseSource::replace)

    override fun getAlbums(pageSize: Int) = databaseSource.getAlbums(pageSize)

    override fun deleteAlbum(album: Entity.Album): Single<ResultState<Int>> =
            databaseSource.deleteAlbum(album).map {
                ResultState.Success(it) as ResultState<Int>
            }.onErrorReturn {
                ResultState.Error(it, null)
            }

    override fun loadAlbums(pageNumber: Int): Single<ResultState<List<Entity.Album>>> = apiSource.getAlbums(pageNumber).map {
        databaseSource.replace(it)
        ResultState.Success(it) as ResultState<List<Entity.Album>>
    }.onErrorReturn {
        ResultState.Error(it, null)
    }
}