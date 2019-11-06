package kz.smart.calendar.modules.schedule.domain

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import kz.smart.calendar.events.CalendarCellChosenEvent
import kz.smart.calendar.events.CalendarDefaultCellEvent
import kz.smart.calendar.modules.schedule.presentation.DataDay
import org.apache.commons.lang3.time.DateUtils
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.properties.Delegates

abstract class CalendarCellAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val context: Context
    private val calendar: Calendar
    private val weekOfMonth: Int
    private val startDate: Calendar
    private val dataDays: ArrayList<DataDay>

    var items: List<Day> by Delegates.observable(emptyList()) { _, old, new ->
        CalendarDiff(old, new).calculateDiff().dispatchUpdatesTo(this)
    }

    constructor(context: Context, calendar: Calendar, startingAt: DayOfWeek, dataDays: ArrayList<DataDay>, preselectedDay: Date? = null) : super() {
        this.context = context
        this.calendar = calendar
        this.dataDays = dataDays
        val start = DateUtils.truncate(calendar, Calendar.DAY_OF_MONTH)
        if (start.get(Calendar.DAY_OF_WEEK) != (startingAt.getDifference() + 1)) {
            start.set(Calendar.DAY_OF_MONTH, if (startingAt.isLessFirstWeek(calendar)) -startingAt.getDifference() else 0)
            start.add(Calendar.DAY_OF_MONTH, -start.get(Calendar.DAY_OF_WEEK) + 1 + startingAt.getDifference())
        }
        startDate = start
        this.weekOfMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) //+ (if (startingAt.isLessFirstWeek(calendar)) 1 else 0) - (if (startingAt.isMoreLastWeek(calendar)) 1 else 0)

        updateItems(preselectedDay)
    }

    fun updateItems(selectedDate: Date? = null) {
        val now = Calendar.getInstance()

        this.items = (0..itemCount).map {
            val cal = Calendar.getInstance().apply { time = startDate.time }
            cal.add(Calendar.DAY_OF_MONTH, it)

            val state = when (calendar.get(Calendar.MONTH).compareTo(cal.get(Calendar.MONTH))) {
                -1 -> DayState.NextMonth
                0 -> DayState.ThisMonth
                1 -> DayState.PreviousMonth
                else -> throw IllegalStateException()
            }
            val isSelected = when (selectedDate) {
                null -> false
                else -> DateUtils.isSameDay(cal.time, selectedDate)
            }
            val isToday = DateUtils.isSameDay(cal, now)

            var dataDay: DataDay? = null
            if (state == DayState.ThisMonth)
            {
                dataDay = dataDays.firstOrNull{dd -> dd.day == cal.get(Calendar.DAY_OF_MONTH)}
                dataDay?.year = cal.get(Calendar.YEAR)
            }
            val day = Day(cal, state, isToday, isSelected, dataDay)
            if (isToday) {
                EventBus.getDefault().post(CalendarDefaultCellEvent(day))
            }
            return@map day
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(holder, items[holder.layoutPosition])
    }

    override fun getItemCount(): Int = 7 * weekOfMonth

    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, day: Day)
}

data class Day(
        var calendar: Calendar,
        var state: DayState,
        var isToday: Boolean,
        var isSelected: Boolean,
        var dataDay: DataDay? = null
)

enum class DayState {
    PreviousMonth,
    ThisMonth,
    NextMonth
}

enum class DayOfWeek {
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday;

    fun getDifference(): Int {
        return when (this) {
            Sunday -> 0
            Monday -> 1
            Tuesday -> 2
            Wednesday -> 3
            Thursday -> 4
            Friday -> 5
            Saturday -> 6
        }
    }

    fun isLessFirstWeek(calendar: Calendar): Boolean {
        return calendar.get(Calendar.DAY_OF_WEEK) < getDifference() + 1
    }

    fun isMoreLastWeek(calendar: Calendar): Boolean {
        val end = DateUtils.truncate(calendar, Calendar.DAY_OF_MONTH)
        end.add(Calendar.MONTH, 1)
        end.add(Calendar.DATE, -1)
        return end.get(Calendar.DAY_OF_WEEK) < getDifference() + 1
    }
}