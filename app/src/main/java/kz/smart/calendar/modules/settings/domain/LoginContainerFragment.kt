package kz.smart.calendar.modules.settings.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login_container.*

import kz.smart.calendar.R
import kz.smart.calendar.ui.adapters.LabeledPagerAdapter

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

        val adapter = LabeledPagerAdapter(childFragmentManager)

        val loginFragment: LoginFragment = LoginFragment.newInstance()
        val registrationFragment: RegistrationFragment = RegistrationFragment.newInstance()

        adapter.addFragment(loginFragment, getString(R.string.login_title))
        adapter.addFragment(registrationFragment, getString(R.string.register_title))

        vp_login_fragments.adapter = adapter
        login_tabs!!.setupWithViewPager(vp_login_fragments)
    }
}
