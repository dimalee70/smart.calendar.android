package kz.smart.calendar.modules.feed.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_feed.*

import kz.smart.calendar.R
import kz.smart.calendar.models.enums.Period
import kz.smart.calendar.models.objects.TestEvent
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter

/**
 * A simple [Fragment] subclass.
 */
class FeedFragment : Fragment() {
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

        val adapter = LabeledPagerAdapter(childFragmentManager)

        val dayFragment: FeedPeriodFragment = FeedPeriodFragment.newInstance(Period.DAY)
        val weekFragment: FeedPeriodFragment = FeedPeriodFragment.newInstance(Period.WEEK)
        val monthFragment: FeedPeriodFragment = FeedPeriodFragment.newInstance(Period.MONTH)

        adapter.addFragment(dayFragment, getString(R.string.today))
        adapter.addFragment(weekFragment, getString(R.string.week))
        adapter.addFragment(monthFragment, getString(R.string.month))

        vp_periods.adapter = adapter
        period_tabs!!.setupWithViewPager(vp_periods)

    }




}
