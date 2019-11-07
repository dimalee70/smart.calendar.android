package kz.smart.calendar.modules.settings.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_login_container.*
import kotlinx.android.synthetic.main.fragment_login_container.view.*
import kotlinx.android.synthetic.main.fragment_settings_container.*
import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentSettingsContainerBinding
import kz.smart.calendar.modules.settings.view.settings.SettingContainerView
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class SettingsContainerFragment : BaseMvpFragment(), SettingContainerView {

    @InjectPresenter
    lateinit var mSettingsContainerPresenter: SettingContinerPresenter

    lateinit var binding: FragmentSettingsContainerBinding

    @ProvidePresenter
    fun providePresenter(): SettingContinerPresenter{
        return SettingContinerPresenter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings_container,
            container,
            false
        )
        binding.indicatorSetting.count = 5
        binding.presenter = mSettingsContainerPresenter
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager(){
        val adapter = LabeledPagerAdapter(childFragmentManager)
        val categoriesFragment: CategoriesFragment = CategoriesFragment.newInstance()
        val optionsFragment: OptionsFragment = OptionsFragment.newInstance()
        val eventHistoryFragment: EventHistoryFragment = EventHistoryFragment.newInstance()
        val subscriptionsFragment: SubscriptionsFragment = SubscriptionsFragment.newInstance()
        val subscribersFragment: SubscribersFragment = SubscribersFragment.newInstance()

//        adapter.addFragment(categoriesFragment, "Категории")
//        adapter.addFragment(optionsFragment, "Желаемые опции")
//        adapter.addFragment(eventHistoryFragment, "История мероприятий")
//        adapter.addFragment(subscriptionsFragment, "Мои подписки")
//        adapter.addFragment(subscribersFragment, "Мои подписчики")

        adapter.addFragment(categoriesFragment, "")
        adapter.addFragment(optionsFragment, "")
        adapter.addFragment(eventHistoryFragment, "")
        adapter.addFragment(subscriptionsFragment, "")
        adapter.addFragment(subscribersFragment, "")

        binding.settingsVp.adapter = adapter
//        binding.settingTabsTl.setupWithViewPager(binding.settingsVp)

        binding.settingsVp.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.indicatorSetting.selection = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}
