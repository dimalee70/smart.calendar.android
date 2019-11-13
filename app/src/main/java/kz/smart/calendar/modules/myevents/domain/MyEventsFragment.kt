package kz.smart.calendar.modules.myevents.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_my_events.*

import kz.smart.calendar.R
import kz.smart.calendar.events.AddEvent
import kz.smart.calendar.events.OpenEventDetailsEvent
import kz.smart.calendar.events.OpenEventDetailsMyEvents
import kz.smart.calendar.models.enums.Period
import kz.smart.calendar.modules.feed.domain.FeedPeriodFragment
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import kz.smart.calendar.ui.common.ZoomOutPageTransformer
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 */
class MyEventsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {

        val illJoin = VisitEventsFragment.newInstance() as BaseMvpFragment
        illJoin.title = getString(R.string.i_will_join_title)
        val createdEventsFragment = CreatedEventsFragment.newInstance() as BaseMvpFragment
        createdEventsFragment.title = getString(R.string.my_events)
        val frags = ArrayList(listOf(illJoin, createdEventsFragment))
        val adapter = LabeledPagerAdapter(this, frags)

        vp_events.setPageTransformer(ZoomOutPageTransformer())
        vp_events.adapter = adapter
        indicator.setViewPager(vp_events)
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
    fun onMessageEvent(event: OpenEventDetailsMyEvents) {
        findNavController().navigate(R.id.action_myEventsFragment_to_nav_event_details)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: AddEvent) {
        findNavController().navigate(R.id.action_myEventsFragment_to_addEventFragment2)
    }
}
