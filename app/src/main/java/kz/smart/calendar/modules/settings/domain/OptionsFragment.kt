package kz.smart.calendar.modules.settings.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentCategoriesBinding
import kz.smart.calendar.databinding.FragmentOptionsBinding
import kz.smart.calendar.modules.settings.presentation.settings.CategoriesPresenter
import kz.smart.calendar.modules.settings.presentation.settings.OptionsPresenter
import kz.smart.calendar.modules.settings.view.settings.OptionsView
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class OptionsFragment : BaseMvpFragment(), OptionsView {

    companion object {
        const val TAG = "OptionsFragment"

        fun newInstance(): OptionsFragment {
            val fragment: OptionsFragment =
                OptionsFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mOptionsPresenter: OptionsPresenter

    lateinit var binding: FragmentOptionsBinding

    @ProvidePresenter
    fun providePresenter(): CategoriesPresenter {
        return CategoriesPresenter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_options,
            container,
            false
        )

        binding.presenter = mOptionsPresenter
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
