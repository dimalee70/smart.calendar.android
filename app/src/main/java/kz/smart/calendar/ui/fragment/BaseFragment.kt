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
import kz.smart.calendar.models.shared.DataHolder


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
            }
            navController.graph = navGraph
        }
        setDestinationListener()
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
            /*else if (destination.id == R.id.loginContainerFragment)
            {
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.settingsContainerFragment, true)
                    .build()
                requireActivity().findNavController(navHostId).navigate(R.id.action_loginContainerFragment_to_settingsContainerFragment, null, navOptions)
            }*/
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