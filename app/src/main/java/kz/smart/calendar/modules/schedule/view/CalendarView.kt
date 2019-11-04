package kz.smart.calendar.modules.schedule.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.modules.schedule.presentation.DataDay

@StateStrategyType(SkipStrategy::class)
interface CalendarView : MvpView {
    fun setItems(days: ArrayList<DataDay>)
}
