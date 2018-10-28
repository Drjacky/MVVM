package ir.hosseinabbasi.data.db.album

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import ir.hosseinabbasi.data.db.BaseDao

/**
 * Created by Dr.jacky on 9/14/2018.
 */
/**
 * Album dao
 */
@Dao
interface AlbumDao : BaseDao<AlbumData.Album> {

    @Query("SELECT * FROM album_table WHERE id = :id")
    override fun select(id: Long): Flowable<AlbumData.Album>

    @Query("SELECT * FROM album_table ORDER BY id")
    override fun selectAllPaged(): DataSource.Factory<Int, AlbumData.Album>

    @Query("DELETE FROM album_table")
    override fun truncate()

    @Transaction
    fun replace(albums: List<AlbumData.Album>) {
        val firstId: Long = albums.firstOrNull()?.id ?: run {
            0L
        }

        val lastId = albums.lastOrNull()?.id ?: run {
            Long.MAX_VALUE
        }

        deleteRange(firstId, lastId)
        insert(albums)
    }

    @Query("DELETE FROM album_table WHERE id BETWEEN :firstId AND :lastId")
    fun deleteRange(firstId: Long, lastId: Long)
}