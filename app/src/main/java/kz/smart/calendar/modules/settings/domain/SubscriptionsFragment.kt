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
import kz.smart.calendar.databinding.FragmentSubscriptionsBinding
import kz.smart.calendar.modules.settings.presentation.settings.EventHistoryPresenter
import kz.smart.calendar.modules.settings.presentation.settings.SubscriptionsPresenter
import kz.smart.calendar.modules.settings.view.settings.SubscriptionsView
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class SubscriptionsFragment : BaseMvpFragment(), SubscriptionsView {

    companion object {
        const val TAG = "SubscriptionsFragment"

        fun newInstance(): SubscriptionsFragment {
            val fragment: SubscriptionsFragment =
                SubscriptionsFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mSubscriptionsPresenter: SubscriptionsPresenter

    lateinit var binding: FragmentSubscriptionsBinding

    @ProvidePresenter
    fun providePresenter(): SubscriptionsPresenter {
        return SubscriptionsPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_subscriptions,
            container,
            false
        )
        binding.presenter = mSubscriptionsPresenter
        // Inflate the layout for this fragment
        return binding.root
    }


}
