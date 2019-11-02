package kz.smart.calendar.modules.settings.domain


import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentRegistrationBinding
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import photograd.kz.smart.presentation.presenter.registration.RegistrationFragmentPresenter
import kz.smart.calendar.modules.settings.presentation.registration.RegistrationProcessPresenter
import photograd.kz.smart.presentation.view.registration.RegistrationFragmentView
import photograd.kz.smart.presentation.view.registration.RegistrationProcessView


const val HEIGHT = "height"

class RegistrationFragment : BaseMvpFragment(),RegistrationFragmentView, RegistrationProcessView {

    companion object {
        const val TAG = "RestorePasswordFragment"

        fun newInstance(): RegistrationFragment {
            val fragment: RegistrationFragment =
                RegistrationFragment()
            val args: Bundle = Bundle()
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
        return view
    }
}
