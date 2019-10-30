package kz.smart.calendar

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kz.smart.calendar.ui.activity.home.HomeActivity
import kz.smart.calendar.ui.activity.LoginInActivity
import kz.smart.calendar.ui.activity.MainAppActivity
import kz.smart.calendar.ui.fragment.home.HomeMainFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainAppActivity::class.java)
        }
    }

    class LoginScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, LoginInActivity::class.java)
        }
    }

    class HomeScreen: SupportAppScreen(){
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    class HomeMainScreen: SupportAppScreen(){
        init {
            this.screenKey = javaClass.simpleName
        }

        override fun getFragment(): Fragment {
            return HomeMainFragment.newInstance()
        }
    }
}