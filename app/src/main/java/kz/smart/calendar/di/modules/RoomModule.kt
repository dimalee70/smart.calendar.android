package kz.smart.calendar.di.modules

import dagger.Module
import androidx.room.Room
import dagger.Provides
import kz.smart.calendar.App
import kz.smart.calendar.models.db.CategoryDao
import kz.smart.calendar.models.db.Db
import kz.smart.calendar.models.db.OptionDao
import kz.smart.calendar.models.db.UserDao
import javax.inject.Singleton


@Module(includes = [ApplicationModule::class])
class RoomModule(private val mApplication: App){
    private val roomDatabase: Db = Room.databaseBuilder(mApplication, Db::class.java, "smart_calendar")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): Db {
        return roomDatabase
    }

    @Singleton
    @Provides
    fun providesUserDao(roomDatabase: Db): UserDao {
        return roomDatabase.getUserDao()
    }

    @Singleton
    @Provides
    fun providesOptionDao(roomDatabase: Db): OptionDao {
        return roomDatabase.getOptionDao()
    }

    @Singleton
    @Provides
    fun providesCategoryDao(roomDatabase: Db): CategoryDao {
        return roomDatabase.getCategoryDao()
    }
}