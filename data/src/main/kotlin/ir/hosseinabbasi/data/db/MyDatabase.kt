package ir.hosseinabbasi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.hosseinabbasi.data.db.album.AlbumDao
import ir.hosseinabbasi.data.db.album.AlbumData

/**
 * Created by Dr.jacky on 9/14/2018.
 */
/**
 * Database class with all of its dao classes
 */
@Database(entities = [AlbumData.Album::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
}