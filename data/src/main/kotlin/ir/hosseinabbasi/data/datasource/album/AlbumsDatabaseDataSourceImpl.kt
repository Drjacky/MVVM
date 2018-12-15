package ir.hosseinabbasi.data.datasource.album

import androidx.paging.DataSource
import ir.hosseinabbasi.data.db.album.AlbumDao
import ir.hosseinabbasi.data.mapper.map
import ir.hosseinabbasi.domain.entity.Entity
import java.util.concurrent.Executor

/**
 * Created by Dr.jacky on 9/28/2018.
 */
/**
 * Album database data source implementation
 */
class AlbumsDatabaseDataSourceImpl(private val albumDao: AlbumDao,
                                   private val ioExecutor: Executor) : AlbumsDatabaseDataSource {

    /**
     * Get all of albums from database implementation
     */
    override fun getAlbums(): DataSource.Factory<Int, Entity.Album> =
            albumDao.selectAllPaged()
                    .map { it.map() }

    /**
     * Persist all of albums in local database implementation
     */
    override fun persist(albums: List<Entity.Album>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            albumDao.insert/*persist*/(albums.map { it.map() })
            insertFinished()
        }
    }

    override fun deleteAlbum(album: Entity.Album) = albumDao.delete(album.map())
}