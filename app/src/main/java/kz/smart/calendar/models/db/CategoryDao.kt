package kz.smart.calendar.models.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable
import io.reactivex.Maybe
import kz.smart.calendar.models.enums.Status
import kz.smart.calendar.models.objects.Category

@Dao
interface CategoryDao {
    @Query("SELECT * from category where status == :status")
    fun getAllActive(status: Status): Flowable<List<Category>>

    @Insert(onConflict = REPLACE)
    fun insert(model: Category): Long

    @Insert(onConflict = REPLACE)
    fun insertAll(models: List<Category>): LongArray

    @Query("DELETE from category")
    fun deleteAll()

    @Query("DELETE from category where id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * from category where id = :id")
    fun getFlowable(id: Int): Flowable<Category>

    @Query("SELECT * from category where id = :id")
    fun get(id: Int): Maybe<Category>

    @Query("SELECT * from category where id = :id")
    fun getSync(id: Int): Category

    @Update
    fun update(model: Category)

    @Delete
    fun delete(model: Category)
}