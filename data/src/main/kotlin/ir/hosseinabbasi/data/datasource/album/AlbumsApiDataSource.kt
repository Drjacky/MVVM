package ir.hosseinabbasi.data.datasource.album

import androidx.paging.PagedList
import io.reactivex.Single
import ir.hosseinabbasi.data.datasource.DataSource
import ir.hosseinabbasi.domain.entity.Entity

/**
 * Created by Dr.jacky on 9/24/2018.
 */
/**
 * Album network data source
 */
interface AlbumsApiDataSource : DataSource {

    /**
     * Get all of albums from network
     */
    fun getAlbums(page: Int): Single<List<Entity.Album>>
}