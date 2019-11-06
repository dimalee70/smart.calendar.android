package kz.smart.calendar.modules.schedule.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import kz.smart.calendar.modules.schedule.domain.Day
import kz.smart.calendar.modules.schedule.presentation.DataDay
import java.util.*
import kotlin.collections.ArrayList

@StateStrategyType(SkipStrategy::class)
interface CalendarView : MvpView {
    fun setItems(days: ArrayList<DataDay>)
    @StateStrategyType(SkipStrategy::class)
    fun unselectItems(dataday: Day?)
}
