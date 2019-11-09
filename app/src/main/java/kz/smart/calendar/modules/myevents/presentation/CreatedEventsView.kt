package kz.smart.calendar.modules.myevents.presentation

import androidx.databinding.ObservableArrayList
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.modules.schedule.domain.Day

@StateStrategyType(SkipStrategy::class)
interface CreatedEventsView : MvpView {
    fun showEvents(events: ObservableArrayList<Event>)
}
