package kz.smart.calendar.models.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.smart.calendar.models.objects.Category
import kz.smart.calendar.models.objects.Option
import kz.smart.calendar.models.objects.User


@Database(entities = [User::class, Category::class, Option::class],
    version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Db : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getOptionDao(): OptionDao
    abstract fun getCategoryDao(): CategoryDao
}