package kz.smart.calendar.modules.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentHomeMainBinding
import kz.smart.calendar.presentation.presenter.home.HomeMainPresenter
import kz.smart.calendar.presentation.view.home.HomeMainView
import kz.smart.calendar.ui.fragment.BaseFragment
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import java.util.*

class HomeMainFragment : BaseMvpFragment(), HomeMainView,
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val TAG = "HomeMainFragment"

        fun newInstance(): HomeMainFragment {
            val fragment: HomeMainFragment = HomeMainFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mHomeMainPresenter: HomeMainPresenter

    @ProvidePresenter
    fun providePresenter(): HomeMainPresenter{
        return HomeMainPresenter()
    }


    lateinit var binding: FragmentHomeMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_main,
            container,
            false
        )
        // setup main view pager
        binding.mainPager.addOnPageChangeListener(this)
        binding.mainPager.adapter = ViewPagerAdapter()
        //binding.mainPager.post(this::checkDeepLink)
        binding.mainPager.offscreenPageLimit = fragments.size

        // set bottom nav
        binding.bottomNav.setOnNavigationItemSelectedListener(this)
        binding.bottomNav.setOnNavigationItemReselectedListener(this)
        // initialize backStack with elements
        if (backStack.empty()) backStack.push(0)

        return binding.root
    }



    // overall back stack of containers
    private val backStack = Stack<Int>()

    // list of base destination containers
    val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_feed_base , R.id.nav_host_feed),
        BaseFragment.newInstance(R.layout.content_calendar_base , R.id.nav_host_calendar),
        BaseFragment.newInstance(R.layout.content_poll_base , R.id.nav_host_poll),
        BaseFragment.newInstance(R.layout.content_events_base , R.id.nav_host_events),
        BaseFragment.newInstance(R.layout.content_settings_base,  R.id.nav_host_settings))
    private val indexToPage = mapOf(0 to R.id.feed, 1 to R.id.calendar, 2 to R.id.poll, 3 to R.id.my_events, 4 to R.id.profile)

    /*var navigator: SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_home_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
        }
    }

    override fun onBackPressed() {
        var fragment = supportFragmentManager.findFragmentById(R.id.activity_home_frame_layout)
        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        }
        else {
            super.onBackPressed()
        }
        finishAffinity()
    }*/

    inner class ViewPagerAdapter : FragmentPagerAdapter(childFragmentManager) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

    }

    /// BottomNavigationView ItemSelected Implementation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (binding.mainPager.currentItem != position) setItem(position)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position]
        fragment.popToRoot()
    }

    /// OnPageSelected Listener Implementation
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(page: Int) {
        val itemId = indexToPage[page] ?: R.id.home
        if (binding.bottomNav.selectedItemId != itemId) binding.bottomNav.selectedItemId = itemId
    }

    private fun setItem(position: Int) {
        binding.mainPager.currentItem = position
        backStack.push(position)
    }

    override fun setBottomViewVisibility(isVisible: Boolean) {
        binding.bottomNav.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


    fun onBackPressed(): Boolean{
        val fragment = fragments[binding.mainPager.currentItem]
        val hadNestedFragments = fragment.onBackPressed()
        // if no fragments were popped
        if (!hadNestedFragments) {
            return if (backStack.size > 1) {
                // remove current position from stack
                backStack.pop()
                // set the next item in stack as current
                binding.mainPager.currentItem = backStack.peek()
                true
            } else false
        }
        return true
    }

}
