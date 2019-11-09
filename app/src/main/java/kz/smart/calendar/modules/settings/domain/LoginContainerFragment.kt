package kz.smart.calendar.modules.settings.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login_container.*

import kz.smart.calendar.R
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayoutMediator


/**
 * A simple [Fragment] subclass.
 */
class LoginContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }



    private fun setupViewPager() {
        val loginFragment: LoginFragment = LoginFragment.newInstance()
        loginFragment.title = getString(R.string.login_title)
        val registrationFragment: RegistrationFragment = RegistrationFragment.newInstance()
        registrationFragment.title = getString(R.string.register_title)
        val adapter = LabeledPagerAdapter(this,  ArrayList(listOf(loginFragment, registrationFragment)))

        vp_login_fragments.adapter = adapter
        TabLayoutMediator(login_tabs, vp_login_fragments, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = (vp_login_fragments.adapter as LabeledPagerAdapter).fragments[position].title
        }).attach()
        indicator.setViewPager(vp_login_fragments)
    }
}
