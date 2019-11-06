package kz.smart.calendar.di.modules

import androidx.databinding.ObservableArrayList
import dagger.Module
import dagger.Provides
import kz.smart.calendar.models.objects.Event
import javax.inject.Singleton

@Module
class EventModule {
    private var event: Event = Event(
        -1,"","", "",-1,"",
        0,0,"","",-1,
        null, ArrayList(),ArrayList(), false, null
    )

    @Provides
    @Singleton
    fun provideEvent(): Event{
        return event
    }
}