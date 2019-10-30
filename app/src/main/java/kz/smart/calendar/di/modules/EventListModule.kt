package kz.smart.calendar.di.modules

import androidx.databinding.ObservableArrayList
import dagger.Module
import dagger.Provides
import kz.smart.calendar.models.objects.Event
import javax.inject.Singleton

@Module
class EventListModule {

    private var events: ObservableArrayList<Event> = ObservableArrayList()

    @Provides
    @Singleton
    fun provideEventList(): ObservableArrayList<Event>{
        return events
    }
}