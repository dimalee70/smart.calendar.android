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
import kz.smart.calendar.Screens
import kz.smart.calendar.presentation.presenter.home.HomePresenter
import kz.smart.calendar.presentation.view.home.HomeView
import kz.smart.calendar.ui.activity.BaseActivity
import kz.smart.calendar.ui.common.BackButtonListener
import kotlinx.android.synthetic.main.activity_home.*
import kz.smart.calendar.modules.home.presentation.HomeMainFragment
import kz.smart.calendar.ui.fragment.BaseFragment
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.util.*
import javax.inject.Inject
import androidx.navigation.fragment.NavHostFragment
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kz.smart.calendar.R


class HomeActivity : BaseActivity(), HomeView{

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

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
         //   navigator.applyCommands(arrayOf<Command>(Replace(Screens.HomeMainScreen())))
        }
        setContentView(R.layout.activity_home)
    }


    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main)
        if (navHostFragment?.childFragmentManager?.fragments?.firstOrNull() is HomeMainFragment) {

            if (!(navHostFragment.childFragmentManager.fragments.first() as HomeMainFragment).onBackPressed()) {
                super.onBackPressed()
            }
        }
    }

    /*private fun checkDeepLink() {
        fragments.forEachIndexed { index, fragment ->
            val hasDeepLink = fragment.handleDeepLink(intent)
            if (hasDeepLink) setItem(index)
        }
    }*/
}
