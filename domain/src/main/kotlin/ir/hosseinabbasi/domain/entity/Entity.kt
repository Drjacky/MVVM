package ir.hosseinabbasi.domain.entity

/**
 * Created by Dr.jacky on 10/9/2018.
 */
/**
 * Album entity
 */
sealed class Entity {

    data class Album(
            val id: Long,
            val title: String,
            val userId: Long
    ) : Entity()
}