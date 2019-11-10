package kz.smart.calendar.modules.schedule.domain


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.tabs.TabLayoutMediator
import kz.smart.calendar.App

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentScheduleBinding
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.models.shared.Utils
import kz.smart.calendar.modules.schedule.presentation.CategorySimple
import kz.smart.calendar.modules.schedule.presentation.SchedulePresenter
import kz.smart.calendar.modules.schedule.view.ScheduleView
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import org.greenrobot.eventbus.EventBus
import java.lang.ClassCastException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ScheduleFragment : BaseMvpFragment(), ScheduleView,
    RecyclerBindingAdapter.OnItemClickListener<Event>{
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
    private var onCustomClickListenerRecycler: RecyclerBindingAdapter.OnItemClickListener<Event>? = this

    lateinit var binding: FragmentScheduleBinding
    var adapter: LabeledPagerAdapter? = null

    @Inject
    lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        simpleCategoryAdapter = RecyclerBindingAdapter(R.layout.category_count_list_item, BR.data, context!!)
        eventsAdapter = RecyclerBindingAdapter(R.layout.item_event, BR.data, context!!)
        if(onCustomClickListenerRecycler != null){
            eventsAdapter.setOnItemClickListener(onCustomClickListenerRecycler!!)
        }
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

        val calendar = Calendar.getInstance()
        val m1 = calendar.get(Calendar.MONTH)
        calendar.add(Calendar.MONTH, 1)
        val m2 = calendar.get(Calendar.MONTH)
        calendar.add(Calendar.MONTH, 1)
        val m3 = calendar.get(Calendar.MONTH)


        val month1: MonthCalendarFragment = MonthCalendarFragment.newInstance(m1, 0)
        month1.title = getMonthFromResource(m1)
        val month2: MonthCalendarFragment = MonthCalendarFragment.newInstance(m2, 1)
        month2.title = getMonthFromResource(m2)
        val month3: MonthCalendarFragment = MonthCalendarFragment.newInstance(m3, 2)
        month3.title = getMonthFromResource(m3)

        adapter = LabeledPagerAdapter(this, ArrayList(listOf(month1, month2, month3)))
        binding.vpMonths.adapter = adapter

        TabLayoutMediator(binding.monthTabs, binding.vpMonths, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = adapter!!.fragments[position].title
        }).attach()

        binding.vpMonths.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val view = adapter!!.fragments[position].view
                if (view != null) {
                    view.post {
                        val wMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY)
                        val hMeasureSpec =
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                        view.measure(wMeasureSpec, hMeasureSpec)

                        if (binding.vpMonths.layoutParams.height != view.measuredHeight) {
                            binding.vpMonths.layoutParams =
                                (binding.vpMonths.layoutParams as LinearLayout.LayoutParams).also { lp ->
                                    lp.height = view.measuredHeight
                                }
                        }
                    }
                }
                else
                {
                    val fragment = (adapter!!.fragments[position] as MonthCalendarFragment)
                    binding.vpMonths.layoutParams =
                        ( binding.vpMonths.layoutParams as LinearLayout.LayoutParams).also { lp ->
                            lp.height = (fragment.getCalendar(position).getActualMaximum(Calendar.WEEK_OF_MONTH) * 54 * Utils.DP).toInt()
                        }
                }
            }
        })
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
            0 -> context!!.getString(R.string.january)
            1 -> context!!.getString(R.string.february)
            2 -> context!!.getString(R.string.march)
            3 -> context!!.getString(R.string.april)
            4 -> context!!.getString(R.string.may)
            5 -> context!!.getString(R.string.june)
            6 -> context!!.getString(R.string.jule)
            7 -> context!!.getString(R.string.august)
            8 -> context!!.getString(R.string.september)
            9 -> context!!.getString(R.string.october)
            10 -> context!!.getString(R.string.november)
            else -> context!!.getString(R.string.december)
        }
    }

    override fun onItemClick(position: Int, item: Event) {
        event.fromEvent(item)
        findNavController().navigate(R.id.action_scheduleFragment_to_eventDetailsFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onCustomClickListenerRecycler = this
        }catch (e: Throwable){
            throw ClassCastException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        onCustomClickListenerRecycler = null
    }
}
