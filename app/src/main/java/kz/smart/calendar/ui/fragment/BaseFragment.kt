package kz.smart.calendar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import kz.smart.calendar.R
import kz.smart.calendar.events.SetBottomBarVisibilityEvent
import kz.smart.calendar.rootDestinations
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open class BaseFragment: Fragment() {

    private val defaultInt = -1
    private var layoutRes: Int = -1
    private var navHostId: Int = -1
    private val appBarConfig = AppBarConfiguration(rootDestinations)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutRes = it.getInt(KEY_LAYOUT)
            navHostId = it.getInt(KEY_NAV_HOST)

        } ?: return
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return if (layoutRes == defaultInt) null
        else inflater.inflate(layoutRes, container, false)
    }

    override fun onStart() {
        super.onStart()

        //we don't hide/show bottom bar for settings
        if (layoutRes != R.layout.content_settings_base)
        {
            setDestinationListener()
        }
    }

    fun setDestinationListener()
    {
        requireActivity().findNavController(navHostId).addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeMainFragment, R.id.myEventsFragment, R.id.scheduleFragment, R.id.feedFragment, R.id.pollFragment, R.id.settingsFragment -> {
                    EventBus.getDefault().post(SetBottomBarVisibilityEvent(true))
                }
                else -> {
                    EventBus.getDefault().post(SetBottomBarVisibilityEvent(false))
                }
            }
        }
    }

    fun onBackPressed(): Boolean {
        return requireActivity()
                .findNavController(navHostId)
                .navigateUp(appBarConfig)
    }


    fun popToRoot() {
        val navController = requireActivity().findNavController(navHostId)
        navController.popBackStack(navController.graph.startDestination, false)
    }

    fun handleDeepLink(intent: Intent) = requireActivity().findNavController(navHostId).handleDeepLink(intent)


    companion object {

        private const val KEY_LAYOUT = "layout_key"
        private const val KEY_NAV_HOST = "nav_host_key"

        fun newInstance(layoutRes: Int, navHostId: Int) = BaseFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_LAYOUT, layoutRes)
                putInt(KEY_NAV_HOST, navHostId)
            }
        }
    }
}