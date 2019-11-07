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
import kz.smart.calendar.databinding.FragmentSubscribersBinding
import kz.smart.calendar.databinding.FragmentSubscriptionsBinding
import kz.smart.calendar.modules.settings.presentation.settings.SubscribersPresenter
import kz.smart.calendar.modules.settings.view.settings.SubscribersView
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class SubscribersFragment: BaseMvpFragment(), SubscribersView {

    companion object {
        const val TAG = "SubscribersFragment"

        fun newInstance(): SubscribersFragment {
            val fragment: SubscribersFragment =
                SubscribersFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mSubscribersPresenter: SubscribersPresenter

    lateinit var binding: FragmentSubscribersBinding

    @ProvidePresenter
    fun providePresenter(): SubscribersPresenter{
        return SubscribersPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_subscribers,
            container,
            false
        )
        binding.presenter = mSubscribersPresenter
        return binding.root
    }


}
