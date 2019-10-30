package photograd.kz.smart.ui.fragment.registration


import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentRegistrationBinding
import kz.smart.calendar.ui.activity.LoginInActivity
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import photograd.kz.smart.presentation.presenter.registration.RegistrationFragmentPresenter
import photograd.kz.smart.presentation.presenter.registration.RegistrationProcessPresenter
import photograd.kz.smart.presentation.view.registration.RegistrationFragmentView
import photograd.kz.smart.presentation.view.registration.RegistrationProcessView


const val HEIGHT = "height"

class RegistrationFragment : BaseMvpFragment(),RegistrationFragmentView, RegistrationProcessView {

    companion object {
        const val TAG = "RestorePasswordFragment"

        fun newInstance(height: Int): RegistrationFragment {
            val fragment: RegistrationFragment =
                RegistrationFragment()
            val args: Bundle = Bundle()
            args.putInt(HEIGHT, height)
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var registrationProcessPresenter: RegistrationProcessPresenter

    @InjectPresenter
    lateinit var registrationPresenter: RegistrationFragmentPresenter

    lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRegistrationBinding>(
            inflater,
            R.layout.fragment_registration,
            container,
            false
        )
        val view = binding.root
        binding.presenter = registrationProcessPresenter

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, arguments!!.getInt(HEIGHT))
        view.layoutParams = params

        return view
    }

    override fun onRegister() {
        val email = binding.registrationEmail.text.toString()
        val username = binding.registrationUsername.text.toString()
        val password = binding.registrationPassword.text.toString()

        if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {

            return
        }

        registrationProcessPresenter.register(
            email = email,
            username = username,
            password = password
        )
    }

    override fun showHome() {
        //(activity as LoginInActivity).goToHome(isAfterRegistration = true)
    }
}
