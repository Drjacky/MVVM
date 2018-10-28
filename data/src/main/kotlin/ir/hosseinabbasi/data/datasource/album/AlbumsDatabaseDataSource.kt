package ir.hosseinabbasi.data.datasource.album

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Flowable
import io.reactivex.Single
import ir.hosseinabbasi.data.datasource.DataSource
import ir.hosseinabbasi.domain.entity.Entity

/**
 * Created by Dr.jacky on 9/24/2018.
 */
/**
 * Album database data source
 */
interface AlbumsDatabaseDataSource : DataSource {

    /**
     * Get all of albums from database implementation
     */
    fun getAlbums(pageSize: Int): Flowable<PagedList<Entity.Album>>

    /**
     * Persist all of albums in local database
     */
    fun replace(albums: List<Entity.Album>)

    fun deleteAlbum(album: Entity.Album): Single<Int>
}