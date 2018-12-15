package ir.hosseinabbasi.domain.repository.album

import androidx.paging.PagedList
import io.reactivex.Flowable
import io.reactivex.Single
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.entity.Entity
import ir.hosseinabbasi.domain.repository.BaseRepository

/**
 * Created by Dr.jacky on 9/23/2018.
 */
/**
 * Album repository
 */
interface AlbumsRepository : BaseRepository {

    /**
     * Perform
     */
    fun getAlbums(): Flowable<ResultState<PagedList<Entity.Album>>>

    fun deleteAlbum(album: Entity.Album): Single<ResultState<Int>>

    //fun loadAlbums(pageNumber: Int): Single<ResultState<List<Entity.Album>>>
}