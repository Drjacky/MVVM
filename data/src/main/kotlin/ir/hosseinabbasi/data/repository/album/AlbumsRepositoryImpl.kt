package ir.hosseinabbasi.data.repository.album

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import ir.hosseinabbasi.data.common.extension.applyIoScheduler
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
    /*override fun getAlbums(pageSize: Int): Flowable<ResultState<PagedList<Entity.Album>>> =
            performList(databaseSource.getAlbums(pageSize),
                    apiSource.getAlbums(pageSize),
                    persist = databaseSource::persist)*/
    override fun getAlbums(): Flowable<ResultState<PagedList<Entity.Album>>> {
        val dataSourceFactory = databaseSource.getAlbums()
        val boundaryCallback = RepoBoundaryCallback(apiSource, databaseSource)
        val data = RxPagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .buildFlowable(BackpressureStrategy.BUFFER)

        return data
                .applyIoScheduler()
                .map { d ->
                    if (d.size > 0)
                        ResultState.Success(d) as ResultState<PagedList<Entity.Album>>
                    else
                        ResultState.Loading(d) as ResultState<PagedList<Entity.Album>>
                }
                .onErrorReturn { e -> ResultState.Error(e, null) }
        //return data.compose(wrapResultFlowable())
    }


    /*override fun getAlbums(): BaseDataSource.Factory<Int, Entity.Album> = object : BaseDataSource.Factory<Int, Entity.Album>() {

        override fun create(): BaseDataSource<Int, Entity.Album> = object : PageKeyedDataSource<Int, Entity.Album>() {

            @SuppressLint("CheckResult")
            override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Entity.Album>) {
                databaseSource.getAlbums(params.requestedLoadSize)
                        .subscribe { list ->
                            callback.onResult(list, null, 2)
                        }
                apiSource.getAlbums(params.requestedLoadSize).subscribe({
                    databaseSource.persist(it)
                }, {})
            }

            @SuppressLint("CheckResult")
            override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Entity.Album>) {
                databaseSource.getAlbums(params.key, params.requestedLoadSize)
                        .subscribe { list ->
                            callback.onResult(list, if (list.isNotEmpty()) params.key + 1 else null)
                        }
                apiSource.getAlbums(params.key, params.requestedLoadSize).subscribe({
                    databaseSource.persist(it)
                }, {})
            }

            @SuppressLint("CheckResult")
            override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Entity.Album>) {
                databaseSource.getAlbums(params.key, params.requestedLoadSize)
                        .subscribe { list ->
                            callback.onResult(list, if (params.key > 1) params.key - 1 else null)
                        }
                apiSource.getAlbums(params.key, params.requestedLoadSize).subscribe({
                    databaseSource.persist(it)
                }, {})
            }
        }
*/
    //override fun getAlbums(pageSize: Int) = databaseSource.getAlbums(pageSize)

    override fun deleteAlbum(album: Entity.Album): Single<ResultState<Int>> =
            databaseSource.deleteAlbum(album).map {
                ResultState.Success(it) as ResultState<Int>
            }.onErrorReturn {
                ResultState.Error(it, null)
            }

/*    override fun loadAlbums(pageNumber: Int): Single<ResultState<List<Entity.Album>>> = apiSource.getAlbums(pageNumber).map {
        databaseSource.persist(it)
        ResultState.Success(it) as ResultState<List<Entity.Album>>
    }.onErrorReturn {
        ResultState.Error(it, null)
    }*/

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}