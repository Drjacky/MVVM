package ir.hosseinabbasi.domain.usecase.album

import androidx.paging.PagedList
import io.reactivex.Flowable
import io.reactivex.Single
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.entity.Entity
import ir.hosseinabbasi.domain.usecase.BaseUseCase

/**
 * Created by Dr.jacky on 10/7/2018.
 */
/**
 * Album use case
 */
interface GetAlbumsUseCase : BaseUseCase {

    /**
     * Get all of albums use case
     */
    fun getAlbums(pageSize: Int = 10): Flowable<PagedList<Entity.Album>>

    fun deleteAlbum(album: Entity.Album): Single<ResultState<Int>>

    fun loadAlbums(pageNumber: Int): Single<ResultState<List<Entity.Album>>>
}