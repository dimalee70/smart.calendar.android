package kz.smart.calendar.modules.schedule.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_schedule.*

import kz.smart.calendar.R
import kz.smart.calendar.events.ScheduleEventDetailsEvent
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.joda.time.DateTime

/**
 * A simple [Fragment] subclass.
 */
class ScheduleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {

        val adapter = LabeledPagerAdapter(childFragmentManager)

        val m1 = DateTime.now().monthOfYear().get()
        val m2 = DateTime.now().plusMonths(1).monthOfYear().get()
        val m3 = DateTime.now().plusMonths(2).monthOfYear().get()


        val month1: MonthCalendarFragment = MonthCalendarFragment.newInstance(m1, 0)
        val month2: MonthCalendarFragment = MonthCalendarFragment.newInstance(m2, 1)
        val month3: MonthCalendarFragment = MonthCalendarFragment.newInstance(m3, 2)

        adapter.addFragment(month1, getMonthFromResource(m1))
        adapter.addFragment(month2, getMonthFromResource(m2))
        adapter.addFragment(month3, getMonthFromResource(m3))

        vp_months.adapter = adapter
        month_tabs!!.setupWithViewPager(vp_months)

    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ScheduleEventDetailsEvent) {
    }

    fun getMonthFromResource(month: Int?): String{
        return when(month) {
            1 -> context!!.getString(R.string.january)
            2 -> context!!.getString(R.string.february)
            3 -> context!!.getString(R.string.march)
            4 -> context!!.getString(R.string.april)
            5 -> context!!.getString(R.string.may)
            6 -> context!!.getString(R.string.june)
            7 -> context!!.getString(R.string.jule)
            8 -> context!!.getString(R.string.august)
            9 -> context!!.getString(R.string.september)
            10 -> context!!.getString(R.string.october)
            11 -> context!!.getString(R.string.november)
            else -> context!!.getString(R.string.december)
        }
    }
}
