package kz.smart.calendar.modules.schedule.domain

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_month_calendar.*
import kz.smart.calendar.App

import kz.smart.calendar.R
import kz.smart.calendar.modules.schedule.presentation.CalendarPresenter
import kz.smart.calendar.modules.schedule.presentation.DataDay
import kz.smart.calendar.modules.schedule.view.CalendarView
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import org.apache.commons.lang3.time.DateUtils
import java.util.*
import kotlin.collections.ArrayList
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import kz.smart.calendar.events.CalendarCellChosenEvent
import kz.smart.calendar.models.shared.Utils
import kz.smart.calendar.ui.common.CircleView
import org.greenrobot.eventbus.EventBus


/**
 * A simple [Fragment] subclass.
 */
class MonthCalendarFragment(base: Calendar = Calendar.getInstance(), val startingAt: DayOfWeek = DayOfWeek.Monday) : BaseMvpFragment(),
    CalendarView {

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

    @InjectPresenter
    lateinit var mPresenter: CalendarPresenter

    @ProvidePresenter
    fun providePresenter(): CalendarPresenter{
        return CalendarPresenter()
    }

    private val baseCalendar: Calendar = DateUtils.truncate(base, Calendar.DAY_OF_MONTH).apply {
        set(Calendar.DAY_OF_MONTH, 1)
        firstDayOfWeek = Calendar.MONDAY + startingAt.getDifference()
        minimalDaysInFirstWeek = 1
    }

    private var viewContainer: ViewGroup? = null
    /*var selectedDay: Date? = null
        set(value) {
            field = value
            notifyCalendarItemChanged()
        }*/
    var onDayClickLister: ((Day) -> Unit)? = null

    var month: Int = 0
    var monthDiff: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        month = arguments!!.getInt(MONTH_EXTRA)
        monthDiff = arguments!!.getInt(MONTH_DIFF_EXTRA)
        mPresenter.reloadData(month, monthDiff)
        return inflater.inflate(R.layout.fragment_month_calendar, container, false)
    }

    override fun setItems(days: ArrayList<DataDay>) {
        if (ScheduleFragment.selectedDate == null && monthDiff == 0) {
            ScheduleFragment.selectedDate = Date()
        }

        rvCalendar.adapter = object : CalendarCellAdapter(context!!, getCalendar(monthDiff), startingAt, days, ScheduleFragment.selectedDate) {
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, day: Day) {
                holder.itemView.setOnClickListener {
                    ScheduleFragment.selectedDate = day.calendar.time
                    this@MonthCalendarFragment.onDayClickLister?.invoke(day)
                    day.isSelected = true
                    notifyCalendarItemChanged()
                    setSelectedViewSate(holder.itemView, isSelected = true, animated = true)
                    EventBus.getDefault().post(CalendarCellChosenEvent(day))
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
            ((views.getChildAt(i) as? RecyclerView)?.adapter as? CalendarCellAdapter)?.updateItems(ScheduleFragment.selectedDate)
        }
    }

    fun onBindView(view: View, day: Day) {
        val txtView = view.findViewById<TextView>(R.id.text_day)
        if (day.state == DayState.ThisMonth) {
            if (day.calendar.get(Calendar.DAY_OF_WEEK) == 1 || day.calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                view.findViewById<ConstraintLayout>(R.id.dayContainer).background = context!!.getDrawable(R.drawable.bg_calendar_holiday)
                txtView.setTextColor(ResourcesCompat.getColor(resources, R.color.secondaryUnselectedBorder, App.instance.theme))
            }
            txtView.text = day.calendar.get(Calendar.DAY_OF_MONTH).toString()

            if (day.dataDay?.topThreeColors?.isNotEmpty() == true)
            {

                val params : LinearLayoutCompat.LayoutParams = LinearLayoutCompat.LayoutParams(7 * Utils.DP,12 * Utils.DP)
                params.setMargins(2 * Utils.DP,0,2 * Utils.DP,0)
                params.gravity = Gravity.CENTER

                val llCircles = view.findViewById<LinearLayoutCompat>(R.id.llCircles)
                day.dataDay!!.topThreeColors.forEach{
                    val circle = CircleView(view.context, it)
                    circle.layoutParams = params
                    llCircles.addView(circle)
                }
            }

            if (day.isSelected)
            {
                setSelectedViewSate(view, true)
            }
        } else {
            view.findViewById<ConstraintLayout>(R.id.dayContainer).visibility = View.GONE
        }
    }

    fun setSelectedViewSate(view: View, isSelected: Boolean, animated: Boolean = false, isHoliday: Boolean = false)
    {
        val txtView = view.findViewById<TextView>(R.id.text_day)
        val selectedView = view.findViewById<View>(R.id.selected_view)
        if (isSelected)
        {
            txtView.setTextColor(ResourcesCompat.getColor(resources, android.R.color.white, App.instance.theme))
            txtView.setTypeface(null, Typeface.BOLD)
            if (animated) {
                val aniFade = AnimationUtils.loadAnimation(context!!, R.anim.fade_in_calendar)
                selectedView.startAnimation(aniFade)
            }
            selectedView.visibility = View.VISIBLE
        }
        else
        {

            txtView.setTypeface(null, Typeface.NORMAL)
            var color: Int = android.R.color.black
            if (isHoliday) {
                color = R.color.secondaryUnselectedBorder
            }
            txtView.setTextColor(ResourcesCompat.getColor(resources, color, App.instance.theme))
            if (animated) {
                val aniFade = AnimationUtils.loadAnimation(context!!, R.anim.fade_out)
                selectedView.startAnimation(aniFade)
            }
            else
            {
                selectedView.visibility = View.INVISIBLE
            }
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

    override fun unselectItems(day: Day?) {
        if (rvCalendar.adapter != null)
        {
            val index = (rvCalendar.adapter as CalendarCellAdapter).items.firstIndexOrNull { it.isSelected && it.calendar.time != day?.calendar?.time }
            if (index != null) {
                run {
                    val prevDate = (rvCalendar.adapter as CalendarCellAdapter).items.firstOrNull { it.isSelected && it.calendar.time != day?.calendar?.time }

                    val viewHolder = rvCalendar.findViewHolderForAdapterPosition(index)
                    setSelectedViewSate(viewHolder!!.itemView, false,
                        animated = day?.dataDay?.month == month,
                        isHoliday = prevDate?.calendar?.get(Calendar.DAY_OF_WEEK) == 1 || prevDate?.calendar?.get(Calendar.DAY_OF_WEEK) == 7)
                    prevDate?.isSelected = false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(mPresenter)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(mPresenter)
    }
}

inline fun <T> Iterable<T>.firstIndexOrNull(predicate: (T) -> Boolean): Int? {
    return this.mapIndexed { index, item -> Pair(index, item) }
        .firstOrNull { predicate(it.second) }
        ?.first
}
