package kz.smart.calendar.ui.activity.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.smart.calendar.App
import kz.smart.calendar.R
import kz.smart.calendar.Screens
import kz.smart.calendar.presentation.presenter.home.HomePresenter
import kz.smart.calendar.presentation.view.home.HomeView
import kz.smart.calendar.ui.activity.BaseActivity
import kz.smart.calendar.ui.common.BackButtonListener
import kotlinx.android.synthetic.main.activity_home.*
import kz.smart.calendar.ui.fragment.BaseFragment
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.util.*
import javax.inject.Inject


class HomeActivity : BaseActivity(), HomeView,
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemReselectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener{

    companion object {
        const val TAG = "HomeActivity"
        fun getIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
        var PHOTO_TRANSITION = "photo_trasition"
    }

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mHomePresenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter{
        return HomePresenter(router)
    }


    // overall back stack of containers
    private val backStack = Stack<Int>()

    // list of base destination containers
    private val fragments = listOf(
        BaseFragment.newInstance(R.layout.content_feed_base , R.id.nav_host_feed),
        BaseFragment.newInstance(R.layout.content_calendar_base , R.id.nav_host_calendar),
        BaseFragment.newInstance(R.layout.content_poll_base , R.id.nav_host_poll),
        BaseFragment.newInstance(R.layout.content_events_base , R.id.nav_host_events),
        BaseFragment.newInstance(R.layout.content_settings_base,  R.id.nav_host_settings))
    private val indexToPage = mapOf(0 to R.id.feed, 1 to R.id.calendar, 2 to R.id.poll, 3 to R.id.my_events, 4 to R.id.profile)


    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
         //   navigator.applyCommands(arrayOf<Command>(Replace(Screens.HomeMainScreen())))
        }
        setContentView(R.layout.activity_home)

        // setup main view pager
        main_pager.addOnPageChangeListener(this)
        main_pager.adapter = ViewPagerAdapter()
        main_pager.post(this::checkDeepLink)
        main_pager.offscreenPageLimit = fragments.size

        // set bottom nav
        bottom_nav.setOnNavigationItemSelectedListener(this)
        bottom_nav.setOnNavigationItemReselectedListener(this)

        // initialize backStack with elements
        if (backStack.empty()) backStack.push(0)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        //navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

    }

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

    inner class ViewPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

    }

    /// BottomNavigationView ItemSelected Implementation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (main_pager.currentItem != position) setItem(position)
        return true
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        val position = indexToPage.values.indexOf(item.itemId)
        val fragment = fragments[position]
        fragment.popToRoot()
    }

    override fun onBackPressed() {
        val fragment = fragments[main_pager.currentItem]
        val hadNestedFragments = fragment.onBackPressed()
        // if no fragments were popped
        if (!hadNestedFragments) {
            if (backStack.size > 1) {
                // remove current position from stack
                backStack.pop()
                // set the next item in stack as current
                main_pager.currentItem = backStack.peek()

            } else super.onBackPressed()
        }
    }

    /// OnPageSelected Listener Implementation
    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(page: Int) {
        val itemId = indexToPage[page] ?: R.id.home
        if (bottom_nav.selectedItemId != itemId) bottom_nav.selectedItemId = itemId
    }

    private fun setItem(position: Int) {
        main_pager.currentItem = position
        backStack.push(position)
    }

    private fun checkDeepLink() {
        fragments.forEachIndexed { index, fragment ->
            val hasDeepLink = fragment.handleDeepLink(intent)
            if (hasDeepLink) setItem(index)
        }
    }
}
