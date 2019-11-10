package kz.smart.calendar.modules.feed.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_feed.*

import kz.smart.calendar.R
import kz.smart.calendar.events.OpenEventDetailsEvent
import kz.smart.calendar.events.SetBottomBarVisibilityEvent
import kz.smart.calendar.models.enums.Period
import kz.smart.calendar.models.objects.TestEvent
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class   FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {

        val dayFragment: FeedPeriodFragment = FeedPeriodFragment.newInstance(Period.DAY)
        dayFragment.title = getString(R.string.today)
        val weekFragment: FeedPeriodFragment = FeedPeriodFragment.newInstance(Period.WEEK)
        weekFragment.title = getString(R.string.week)
        val monthFragment: FeedPeriodFragment = FeedPeriodFragment.newInstance(Period.MONTH)
        monthFragment.title = getString(R.string.month)
        val adapter = LabeledPagerAdapter(this, ArrayList(listOf(dayFragment, weekFragment, monthFragment)))

        vp_periods.adapter = adapter
        TabLayoutMediator(period_tabs, vp_periods, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = (vp_periods.adapter as LabeledPagerAdapter).fragments[position].title
        }).attach()
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
    fun onMessageEvent(event: OpenEventDetailsEvent) {
        findNavController().navigate(R.id.action_feedFragment_to_nav_event_details)
    }
}
