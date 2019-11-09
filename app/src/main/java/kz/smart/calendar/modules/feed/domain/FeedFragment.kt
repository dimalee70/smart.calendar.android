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

    lateinit var recyclerTypesAdapter: RecyclerBindingAdapter<TestEvent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment

//
//        val testEventStateList = ArrayList<TestEventState>();
//        testEventStateList.add(TestEventState("https://myrealdomain.com/images/instagram-icon-url-1.png"))
//        testEventStateList.add(TestEventState("https://myrealdomain.com/images/instagram-icon-url-1.png"))
//        testEventStateList.add(TestEventState("https://myrealdomain.com/images/instagram-icon-url-1.png"))
//        testEventStateList.add(TestEventState("https://myrealdomain.com/images/instagram-icon-url-1.png"))
//        testEventStateList.add(TestEventState("https://myrealdomain.com/images/instagram-icon-url-1.png"))
//        testEventStateList.add(TestEventState("https://myrealdomain.com/images/instagram-icon-url-1.png"))
//
//        val testEventList = ArrayList<TestEvent>()
//        testEventList.add(TestEvent("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRML7nhq33SEO38hPxkqGLWQNMM2w8-ymSvFXRRBAHFsspoEtpD&s",
//            "12:00", "Кабанбай батыра уг. Наурызбай батыра",
//            "Музей Алматы", "Ночь в музее", "Культура", "2627",
//            "#94A1F0", "450", "https://cdn0.iconfinder.com/data/icons/iconshock_guys/512/andrew.png",
//            "https://cdn6.f-cdn.com/contestentries/918774/22954115/586eea98be949_thumb900.jpg",
//            "https://media.vanityfair.com/photos/58c2f5aa0a144505fae9e9ee/master/pass/avatar-sequels-delayed.jpg",
//            testEventStateList
//        )
//        )
//
//        testEventList.add(TestEvent("https://images.pexels.com/photos/462118/pexels-photo-462118.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
//            "12:00", "Кабанбай батыра уг. Наурызбай батыра",
//            "Музей Алматы", "Ночь в музее", "Культура", "2627",
//            "#94A1F0", "450", "https://cdn0.iconfinder.com/data/icons/iconshock_guys/512/andrew.png",
//            "https://cdn6.f-cdn.com/contestentries/918774/22954115/586eea98be949_thumb900.jpg",
//            "https://media.vanityfair.com/photos/58c2f5aa0a144505fae9e9ee/master/pass/avatar-sequels-delayed.jpg",
//            testEventStateList
//        ))
//
//        types.addAll(testEventList)
////        customs.addAll(customList)
//        recyclerTypesAdapter.setItems(types)
//
//        binding.typesRv.adapter = recyclerTypesAdapter
//        binding.customsRv.adapter = recyclerCustomsAdapter


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
                /*var height = 0
                for (i in 0 until childCount) {
                    val child = getChildAt(i)
                    child.measure(
                        widthMeasureSpec,
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )
                    val h = child.measuredHeight
                    if (h > height) height = h
                }

                var heightMeasureSpec1 = heightMeasureSpec
                if (height != 0) {
                    heightMeasureSpec1 = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
                }*/
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
