package ir.hosseinabbasi.data.db.album

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Dr.jacky on 10/12/2018.
 */
/**
 * Album data class
 */
sealed class AlbumData {

    @Entity(tableName = "album_table")
    data class Album(@ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Long,
                     @ColumnInfo(name = "user_id") val userId: Long,
                     @ColumnInfo(name = "title") val title: String) : AlbumData()

    @Entity(tableName = "album_photo_table")
    data class Photo(@ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Long,
                     @ColumnInfo(name = "album_id") val albumId: Long,
                     @ColumnInfo(name = "title") val title: String,
                     @ColumnInfo(name = "url") val url: String,
                     @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String) : AlbumData()
}