package ir.hosseinabbasi.data.datasource.album

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import ir.hosseinabbasi.data.db.album.AlbumDao
import ir.hosseinabbasi.data.mapper.map
import ir.hosseinabbasi.domain.entity.Entity

/**
 * Created by Dr.jacky on 9/28/2018.
 */
/**
 * Album database data source implementation
 */
class AlbumsDatabaseDataSourceImpl(private val albumDao: AlbumDao) : AlbumsDatabaseDataSource {

    /**
     * Get all of albums from database implementation
     */
    override fun getAlbums(pageSize: Int): Flowable<PagedList<Entity.Album>> {
        val dataSourceFactory = albumDao.selectAllPaged().map { it.map() }
        val pageListConfig = PagedList.Config.Builder().setPageSize(pageSize).setInitialLoadSizeHint(pageSize).setEnablePlaceholders(true).build()
        return RxPagedListBuilder(dataSourceFactory, pageListConfig).buildFlowable(BackpressureStrategy.BUFFER)
    }

    /**
     * Persist all of albums in local database implementation
     */
    override fun replace(albums: List<Entity.Album>) {
        albumDao.replace(albums.map { it.map() })
    }

    override fun deleteAlbum(album: Entity.Album) = albumDao.delete(album.map())
}