package ir.hosseinabbasi.data.mapper

import ir.hosseinabbasi.data.db.album.AlbumDao
import ir.hosseinabbasi.data.db.album.AlbumData
import ir.hosseinabbasi.domain.entity.Entity

/**
 * Created by Dr.jacky on 10/10/2018.
 */
/**
 * Extension class to map album data to album entity
 */
fun AlbumData.Album.map() = Entity.Album(
        id = id,
        userId = userId,
        title = title
)