package kz.smart.calendar.models.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import io.reactivex.Maybe
import kz.smart.calendar.models.enums.Status
import kz.smart.calendar.models.objects.Option

@Dao
interface OptionDao {

    @Query("SELECT * from option where status == :status")
    fun getAllActive(status: Status): Flowable<List<Option>>

    @Insert(onConflict = REPLACE)
    fun insert(model: Option): Long

    @Insert(onConflict = REPLACE)
    fun insertAll(models: List<Option>): LongArray

    @Query("DELETE from option")
    fun deleteAll()

    @Query("DELETE from option where id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * from option where id = :id")
    fun getFlowable(id: Int): Flowable<Option>

    @Query("SELECT * from option where id = :id")
    fun get(id: Int): Maybe<Option>

    @Query("SELECT * from option where id = :id")
    fun getSync(id: Int): Option

    @Update
    fun update(model: Option)

    @Delete
    fun delete(model: Option)
}