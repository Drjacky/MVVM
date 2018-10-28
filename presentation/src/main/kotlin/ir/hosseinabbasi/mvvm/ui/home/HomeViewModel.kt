package ir.hosseinabbasi.mvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.entity.Entity
import ir.hosseinabbasi.domain.usecase.album.GetAlbumsUseCase
import ir.hosseinabbasi.mvvm.common.OperationLiveData
import ir.hosseinabbasi.mvvm.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by Dr.jacky on 9/21/2018.
 */
class HomeViewModel @Inject constructor(private val getAlbumsUseCase: GetAlbumsUseCase) : BaseViewModel() {

    var page = 0
    private val albumToBeDeleted = MutableLiveData<Entity.Album>()
    private val pageLiveData = MutableLiveData<Int>()
//    val pageNumberLiveData = MutableLiveData<Int>().defaultValue(1)

    val deletedAlbumLiveData: LiveData<ResultState<Int>> = Transformations.switchMap(albumToBeDeleted) {
        OperationLiveData<ResultState<Int>> {
            getAlbumsUseCase.deleteAlbum(it).toFlowable().subscribe { resultState ->
                postValue(resultState)
            }
        }
    }

    fun deleteAlbum(album: Entity.Album) {
        albumToBeDeleted.postValue(album)
    }

//    val albumsLiveData: LiveData<ResultState<PagedList<Entity.Album>>> = Transformations.switchMap(pageLiveData) {
//        OperationLiveData<PagedList<Entity.Album>> {
//            getAlbumsUseCase.getAlbums(it).subscribe { resultState ->
//                postValue((resultState))
//                when (resultState) { is ResultState.Success ->
//                    pageNumberLiveData.postValue(resultState.data.size)
//                }
//            }
//        }
//    }

    val albumsLiveData: LiveData<PagedList<Entity.Album>> = OperationLiveData<PagedList<Entity.Album>> {
        getAlbumsUseCase.getAlbums().subscribe {
            postValue(it)
        }
    }

    fun getAlbums() {
        page += 10
        pageLiveData.postValue(page)
    }

    val networkList: LiveData<ResultState<List<Entity.Album>>> = Transformations.switchMap(pageLiveData) {
        OperationLiveData<ResultState<List<Entity.Album>>> {
            getAlbumsUseCase.loadAlbums(it).subscribe { resultState ->
                postValue(resultState)
            }
        }
    }
}