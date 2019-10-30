package photograd.kz.smart.ui.fragment.login

import androidx.databinding.DataBindingUtil
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentLoginBinding
import kz.smart.calendar.ui.activity.LoginInActivity
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import photograd.kz.smart.presentation.presenter.login.LoginPresenter
import photograd.kz.smart.presentation.presenter.login.LoginProcessPresenter
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

    @InjectPresenter
    lateinit var mLoginPresenter: LoginPresenter

    @InjectPresenter
    lateinit var mLoginProcessPresenter: LoginProcessPresenter

    lateinit var binding: FragmentLoginBinding

    //lateinit var mLoadingSpinner: LoadingSpinner

    override fun onLoginClick()
    {
        mLoginProcessPresenter.logIn(binding.user!!)
    }

    override fun onRegisterClick()
    {
        view?.let {
            (activity as LoginInActivity).showRegistrationView(it.height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater , R.layout.fragment_login, container, false)
        var frView : View  = binding.root

        binding.user = mLoginPresenter.user
        binding.presenter = mLoginProcessPresenter
        //binding.tvForgotPassord.paintFlags = (binding.tvForgotPassord.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
        //binding.tvRegister.paintFlags = (binding.tvRegister.paintFlags or Paint.UNDERLINE_TEXT_FLAG)
        return frView
    }

    override fun showHome()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
