package kz.smart.calendar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import kz.smart.calendar.R
import kz.smart.calendar.events.SetBottomBarVisibilityEvent
import kz.smart.calendar.rootDestinations
import org.greenrobot.eventbus.EventBus
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kz.smart.calendar.events.GoToProfileEvent
import kz.smart.calendar.events.OpenEventDetailsEvent
import kz.smart.calendar.models.shared.DataHolder
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


open class BaseFragment: BaseMvpFragment() {

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
        return if (layoutRes == defaultInt) null
        else inflater.inflate(layoutRes, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Inflate the layout for this fragment
        if (layoutRes == R.layout.content_settings_base)
        {
            val navController = requireActivity().findNavController(navHostId)
            val navGraph = navController.navInflater.inflate(R.navigation.main_graph_settings)
            if(DataHolder.user?.username != null)
            {
                navGraph.startDestination = R.id.settingsContainerFragment
            }
            else
            {
                navGraph.startDestination = R.id.loginContainerFragment
                EventBus.getDefault().register(this)
            }
            navController.graph = navGraph
        }
        setDestinationListener()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: GoToProfileEvent) {
        val navController = requireActivity().findNavController(navHostId)
        val navGraph = navController.navInflater.inflate(R.navigation.main_graph_settings)
        navGraph.startDestination = R.id.settingsContainerFragment
        navController.graph = navGraph
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this)
        }
    }

    fun setDestinationListener()
    {
        requireActivity().findNavController(navHostId).addOnDestinationChangedListener { _, destination, _ ->
            if (layoutRes != R.layout.content_settings_base) {
                when (destination.id) {
                    R.id.homeMainFragment, R.id.myEventsFragment, R.id.scheduleFragment, R.id.feedFragment, R.id.pollFragment -> {
                        EventBus.getDefault().post(SetBottomBarVisibilityEvent(true))
                    }
                    else -> {
                        EventBus.getDefault().post(SetBottomBarVisibilityEvent(false))
                    }
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