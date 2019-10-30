package kz.smart.calendar.modules.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.tabs.TabLayout
import kz.smart.calendar.R
import kz.smart.calendar.modules.home.data.model.NamedPagerElements
import kz.smart.calendar.modules.home.domain.HomeFlowPresenter
import kz.smart.calendar.modules.home.domain.HomeFlowView
import kz.smart.calendar.ui.fragment.BaseMvpFragment

private const val MIN_PAGES_COUNT = 5

class HomeFlowFragment : BaseMvpFragment(), HomeFlowView, Tagger {

    companion object {
        const val TAG: String = "HomeFlowFragment"

        fun newInstance() = HomeFlowFragment()
    }

   // lateinit var binding: FragmentHomeFlowBinding

    private var namedPagesList: ArrayList<NamedPagerElements>? = null

    @InjectPresenter
    lateinit var presenter: HomeFlowPresenter


    private var pagesCount: Int = MIN_PAGES_COUNT

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       /* binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_flow,
            container,
            false
        )
        //presenter.getStoreCategories()
        showBottomNavigation()

        return binding.root*/
        return null
    }

    override fun initViewPager() {
       // binding.vpMain.adapter = HomeViewPagerAdapter(childFragmentManager, pagesCount = pagesCount, tagger = this)
        //binding.vpMain.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayoutMain))
    }

    override fun getTag(position: Int): String? {
        if (namedPagesList!!.size-1 < position){
            return null
        }
        return namedPagesList?.get(position)?.tag
    }

    override fun getId(position: Int): Int? {
        if (namedPagesList!!.size-1 < position){
            return null
        }
        return namedPagesList?.get(position)?.categoryId
    }

    private fun addTabs() {
        namedPagesList = ArrayList()
        /*binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_duels))
        addPagerElement(MissionsFragment.TAG)

        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_missions))
        addPagerElement(MissionsFragment.TAG)

        pagesCount += categories.size

        for (i in categories.indices) {
            binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(categories[i].title))
            addPagerElement(tag = StoreCategoryFragment.TAG, id = categories[i].id)
        }

        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_top_rating))
        addPagerElement(RatingFragment.TAG)
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_friends_rating))
        addPagerElement(FriendsRatingFragment.TAG)
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_base_settings))
        addPagerElement(BaseSettingsFragment.TAG)

        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_profile))
        addPagerElement(ProfileFragment.TAG)

        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_achievements))
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setText(R.string.tab_games_history))*/
    }

    private fun addPagerElement(tag: String) {
        addPagerElement(tag = tag, id = null)
    }

    private fun addPagerElement(tag: String, id: Int?) {
        namedPagesList?.add(NamedPagerElements(tag = tag, categoryId = id))
    }

    private fun showBottomNavigation() {
        /*var bottomNavFragment =
            childFragmentManager.findFragmentById(R.id.bottom_nav_container) as BottomNavigationFragment?

        if (bottomNavFragment == null) {
            bottomNavFragment =
                BottomNavigationFragment.newInstance()

            childFragmentManager
                .beginTransaction()
                .add(R.id.bottom_nav_container, bottomNavFragment)
                .commit()
        }*/
    }


}
