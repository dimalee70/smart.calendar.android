package kz.smart.calendar.models.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import io.reactivex.Maybe
import kz.smart.calendar.models.objects.User

@Dao
interface UserDao {

    @Query("SELECT * from user")
    fun getAllActive(): Flowable<List<User>>

    @Insert(onConflict = REPLACE)
    fun insert(model: User): Long

    @Insert(onConflict = REPLACE)
    fun insertAll(models: List<User>): LongArray

    @Query("DELETE from user")
    fun deleteAll()

    @Query("DELETE from user where id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * from user where id = :id")
    fun getFlowable(id: Int): Flowable<User>

    @Query("SELECT * from user where id = :id")
    fun get(id: String): Maybe<User>

    @Update
    fun update(model: User)

    @Delete
    fun delete(model: User)
}