package kz.smart.calendar.modules.schedule.domain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_month_calendar.*
import kz.smart.calendar.App

import kz.smart.calendar.R
import org.apache.commons.lang3.time.DateUtils
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MonthCalendarFragment(base: Calendar = Calendar.getInstance(), val startingAt: DayOfWeek = DayOfWeek.Monday) : Fragment() {

    companion object {
        private const val MONTH_EXTRA: String = "MONTH_EXTRA"
        private const val MONTH_DIFF_EXTRA: String = "MONTH_DIFF_EXTRA"

        fun newInstance(month: Int, monthDiff: Int): MonthCalendarFragment {
            val f = MonthCalendarFragment()
            val bdl = Bundle(1)
            bdl.putInt(MONTH_EXTRA, month)
            bdl.putInt(MONTH_DIFF_EXTRA, monthDiff)
            f.arguments = bdl
            return f

        }
    }

    private val baseCalendar: Calendar = DateUtils.truncate(base, Calendar.DAY_OF_MONTH).apply {
        set(Calendar.DAY_OF_MONTH, 1)
        firstDayOfWeek = Calendar.MONDAY + startingAt.getDifference()
        minimalDaysInFirstWeek = 1
    }

    private var viewContainer: ViewGroup? = null
    var selectedDay: Date? = null
        set(value) {
            field = value
            notifyCalendarItemChanged()
        }
    var onDayClickLister: ((Day) -> Unit)? = null

    var month: Int = 0
    var monthDiff: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        month = arguments!!.getInt(MONTH_EXTRA)
        monthDiff = arguments!!.getInt(MONTH_DIFF_EXTRA)
        return inflater.inflate(R.layout.fragment_month_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvCalendar.adapter = object : CalendarCellAdapter(context!!, getCalendar(monthDiff), startingAt, selectedDay) {
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, day: Day) {
                holder.itemView.setOnClickListener {
                    this@MonthCalendarFragment.selectedDay = day.calendar.time
                    this@MonthCalendarFragment.onDayClickLister?.invoke(day)
                    notifyCalendarItemChanged()
                }
                holder.itemView.setOnLongClickListener {
                    false
                }
                this@MonthCalendarFragment.onBindView(holder.itemView, day)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                object : RecyclerView.ViewHolder(this@MonthCalendarFragment.onCreateView(parent, viewType)) {}
        }
    }

    private fun notifyCalendarItemChanged() {
        val views = viewContainer ?: return
        (0 until views.childCount).forEach { i ->
            ((views.getChildAt(i) as? RecyclerView)?.adapter as? CalendarCellAdapter)?.updateItems(selectedDay)
        }
    }

    fun onBindView(view: View, day: Day) {
        if (day.state == DayState.ThisMonth) {
            if (day.calendar.get(Calendar.DAY_OF_WEEK) == 1 || day.calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                view.findViewById<ConstraintLayout>(R.id.dayContainer).background = context!!.getDrawable(R.drawable.bg_calendar_holiday)
                view.findViewById<TextView>(R.id.text_day).setTextColor(ResourcesCompat.getColor(resources, R.color.secondaryUnselectedBorder, App.instance.theme))
            }

            view.findViewById<View>(R.id.view_dot).visibility = if (day.isSelected) View.VISIBLE else View.GONE
            view.findViewById<TextView>(R.id.text_day).text = day.calendar.get(Calendar.DAY_OF_MONTH).toString()
        } else {
            view.findViewById<ConstraintLayout>(R.id.dayContainer).visibility = View.GONE
        }
    }

    fun onCreateView(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.view_calendar_cell, parent, false)
    }

    fun getCalendar(position: Int): Calendar {
        return (baseCalendar.clone() as Calendar).apply {
            add(Calendar.MONTH, position)
        }
    }
}
