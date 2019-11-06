package kz.smart.calendar.modules.settings.domain

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag
import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentLoginBinding
import kz.smart.calendar.ui.activity.LoginInActivity
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import photograd.kz.smart.presentation.presenter.login.LoginPresenter
import kz.smart.calendar.modules.settings.presentation.login.LoginProcessPresenter
import photograd.kz.smart.presentation.view.login.LoginFragmentView
import photograd.kz.smart.presentation.view.login.LoginProcessView

class LoginFragment : BaseMvpFragment(), LoginFragmentView, LoginProcessView {

    companion object {
        const val TAG = "LoginFragment"

        fun newInstance(): LoginFragment {
            val fragment: LoginFragment =
                LoginFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var mLoginPresenter: LoginPresenter

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var mLoginProcessPresenter: LoginProcessPresenter

    @ProvidePresenterTag(presenterClass = LoginPresenter::class, type = PresenterType.GLOBAL)
    fun provideLoginPresenterTag(): String = LoginFragment.TAG

    @ProvidePresenterTag(presenterClass = LoginProcessPresenter::class, type = PresenterType.GLOBAL)
    fun provideLoginProcessPresenterTag(): String = LoginFragment.TAG + "_process"

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun mLoginPresenter() = LoginPresenter()

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun mLoginProcessPresenter() = LoginProcessPresenter()

    lateinit var binding: FragmentLoginBinding

    //lateinit var mLoadingSpinner: LoadingSpinner

    override fun onLoginClick()
    {
        mLoginProcessPresenter.logIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater , R.layout.fragment_login, container, false)
        var frView : View  = binding.root

        binding.user = mLoginProcessPresenter.user
        binding.presenter = mLoginProcessPresenter
        //binding.tvForgotPassord.paintFlags = (binding.tvForgotPassord.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
        //binding.tvRegister.paintFlags = (binding.tvRegister.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
        return frView
    }

    override fun validateUserData(email: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validateUserDataSuccessfull() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validateUserDataError(email: Int, password: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
