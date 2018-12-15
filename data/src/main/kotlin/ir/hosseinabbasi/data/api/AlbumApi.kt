package ir.hosseinabbasi.data.api

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Dr.jacky on 9/19/2018.
 */
/**
 * Albums api
 */
interface AlbumApi {

    /**
     * Get all albums
     */
    @GET("albums")
    fun getAlbums(@Query("_start") page: Int,
                  @Query("_limit") pageSize: Int): Single<List<Dto.Album>>

    /**
     * Get an album photo by the album id
     */
    @GET("albums/{albumId}/photos")
    fun getPhotosByAlbumId(@Path("albumId") albumId: Int): Single<List<Dto.Photo>>

    sealed class Dto {

        data class Album(
                @SerializedName("id") val id: Long,
                @SerializedName("userId") val userId: Long,
                @SerializedName("title") val title: String
        ) : Dto()

        data class Photo(
                @SerializedName("id") val id: Long,
                @SerializedName("albumId") val albumId: Long,
                @SerializedName("title") val title: String,
                @SerializedName("url") val url: String,
                @SerializedName("thumbnailUrl") val thumbnailUrl: String
        ) : Dto()
    }
}