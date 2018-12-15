package ir.hosseinabbasi.data.datasource.album

import android.annotation.SuppressLint
import io.reactivex.Single
import ir.hosseinabbasi.data.datasource.BaseDataSource
import ir.hosseinabbasi.domain.common.ResultState
import ir.hosseinabbasi.domain.entity.Entity

/**
 * Created by Dr.jacky on 9/24/2018.
 */
@SuppressLint("CheckResult")
fun getAlbums(
        apiSource: AlbumsApiDataSource,
        page: Int,
        itemsPerPage: Int,
        onResult: (result: ResultState<List<Entity.Album>>) -> Unit
) {
    apiSource.getAlbums((page - 1) * itemsPerPage , itemsPerPage)
            .subscribe({ data ->
                onResult(ResultState.Success(data))
            }, { throwable ->
                onResult(ResultState.Error(throwable, null))
            })
}

/**
 * Album network data source
 */
interface AlbumsApiDataSource : BaseDataSource {

    /**
     * Get all of albums from network
     */
    fun getAlbums(page: Int, pageSize: Int): Single<List<Entity.Album>>
}