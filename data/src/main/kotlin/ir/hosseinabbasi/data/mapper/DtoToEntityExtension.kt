package ir.hosseinabbasi.data.mapper

import ir.hosseinabbasi.data.api.AlbumApi
import ir.hosseinabbasi.domain.entity.Entity

/**
 * Created by Dr.jacky on 10/10/2018.
 */
/**
 * Extension class to map album dto to album entity
 */
fun AlbumApi.Dto.Album.map() = Entity.Album(
        id = id,
        userId = userId,
        title = title
)
