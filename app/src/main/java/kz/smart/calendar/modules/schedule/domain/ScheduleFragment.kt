package kz.smart.calendar.modules.schedule.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_schedule.*
import kz.smart.calendar.App

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentScheduleBinding
import kz.smart.calendar.events.ScheduleEventDetailsEvent
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.models.shared.Utils
import kz.smart.calendar.modules.schedule.presentation.CalendarPresenter
import kz.smart.calendar.modules.schedule.presentation.CategorySimple
import kz.smart.calendar.modules.schedule.presentation.DataDay
import kz.smart.calendar.modules.schedule.presentation.SchedulePresenter
import kz.smart.calendar.modules.schedule.view.ScheduleView
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList

class ScheduleFragment : BaseMvpFragment(), ScheduleView {
    companion object {
        const val TAG = "ScheduleFragment"
        var selectedDate: Date? = null

        fun newInstance(): ScheduleFragment {
            val fragment = ScheduleFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
    @InjectPresenter
    lateinit var mPresenter: SchedulePresenter

    @ProvidePresenter
    fun providePresenter(): SchedulePresenter {
        return SchedulePresenter()
    }

    lateinit var simpleCategoryAdapter: RecyclerBindingAdapter<CategorySimple>
    lateinit var eventsAdapter: RecyclerBindingAdapter<Event>

    lateinit var binding: FragmentScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        simpleCategoryAdapter = RecyclerBindingAdapter(R.layout.category_count_list_item, BR.data, context!!)
        eventsAdapter = RecyclerBindingAdapter(R.layout.item_event, BR.data, context!!)
        //simpleCategoryAdapter.setItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        binding.presenter = mPresenter
        binding.recyclerview.adapter = simpleCategoryAdapter
        binding.rvEvents.adapter = eventsAdapter
        return binding.root
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

    override fun showSelectedDate(day: Day?)
    {
        binding.txtDay.text = "${day?.calendar?.get(Calendar.DAY_OF_MONTH)} ${Utils.getOfMonthFromResource(day?.calendar?.get(Calendar.MONTH))}"


        if (day?.dataDay?.categories?.isNotEmpty() != true)
        {
            binding.recyclerview.visibility = View.GONE
            simpleCategoryAdapter.setItems(ObservableArrayList())
            return
        }
        binding.recyclerview.visibility = View.VISIBLE
        val list = ObservableArrayList<CategorySimple>()
        list.addAll(day.dataDay!!.categories)
        simpleCategoryAdapter.setItems(list)
        simpleCategoryAdapter.notifyDataSetChanged()
    }

    override fun showEvents(events: ObservableArrayList<Event>) {
        eventsAdapter.setItems(events)
        eventsAdapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(mPresenter)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(mPresenter)
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
