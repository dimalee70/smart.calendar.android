package kz.smart.calendar.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.arellomobile.mvp.presenter.InjectPresenter
import kz.smart.calendar.R
import kz.smart.calendar.Screens
import kz.smart.calendar.presentation.presenter.LoginInPresenter
import kz.smart.calendar.presentation.view.login.LoginInView
import kz.smart.calendar.ui.common.BackButtonListener
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace

class LoginInActivity : BaseActivity(), LoginInView {

    private var doubleBackToExitPressedOnce = false

    companion object {
        const val TAG = "LoginInActivity"
        fun getIntent(context: Context): Intent = Intent(context, LoginInActivity::class.java)
        val LOGIN_TRANSITION = "login_transition"
    }

    @InjectPresenter
    lateinit var mLoginInPresenter: LoginInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_in)


        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Screens.HomeMainScreen())))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    var navigator:SupportAppNavigator = object : SupportAppNavigator(this, R.id.activity_login_frame_layout) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction?
        ) {
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.activity_login_frame_layout)
        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        }
        else {
            super.onBackPressed()
        }
    }

    override fun showRegistrationView(height: Int) {
        /*val registrationFragment = RegistrationFragment.newInstance(height = height)

        supportFragmentManager.beginTransaction()
            .replace(R.id.activity_login_frame_layout, registrationFragment)
            .addToBackStack(null)
            .commit()*/
    }

}
