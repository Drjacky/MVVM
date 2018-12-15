package ir.hosseinabbasi.domain.usecase.album

import androidx.paging.PagedList
import io.reactivex.Flowable
import io.reactivex.Single
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.common.transformer.FTransformer
import ir.hosseinabbasi.domain.common.transformer.STransformer
import ir.hosseinabbasi.domain.entity.Entity
import ir.hosseinabbasi.domain.repository.album.AlbumsRepository

/**
 * Created by Dr.jacky on 9/14/2018.
 */
/**
 * Album use case implementation
 */
class GetAlbumsUseCaseImpl(
        private val transformerFlowable: FTransformer<ResultState<PagedList<Entity.Album>>>,
        private val transformerSingle: STransformer<ResultState<Int>>,
        private val transformerSingleList: STransformer<ResultState<List<Entity.Album>>>,
        private val repository: AlbumsRepository) : GetAlbumsUseCase {

    /**
     * Get all of albums use case implementation
     */
    override fun getAlbums(): Flowable<ResultState<PagedList<Entity.Album>>> =
            repository.getAlbums()/*.compose(transformerFlowable)*/

    override fun deleteAlbum(album: Entity.Album) = repository.deleteAlbum(album).compose(transformerSingle)

    /*override fun loadAlbums(pageNumber: Int): Single<ResultState<List<Entity.Album>>> =
            repository.loadAlbums(pageNumber).compose(transformerSingleList)*/
}