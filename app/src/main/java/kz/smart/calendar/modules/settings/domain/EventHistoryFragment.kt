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
import kz.smart.calendar.databinding.FragmentEventHistoryBinding
import kz.smart.calendar.modules.settings.presentation.settings.EventHistoryPresenter
import kz.smart.calendar.modules.settings.view.settings.EventHistoryView
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class EventHistoryFragment : BaseMvpFragment(), EventHistoryView{

    companion object {
        const val TAG = "EventHistoryFragment"

        fun newInstance(): EventHistoryFragment {
            val fragment: EventHistoryFragment =
                EventHistoryFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mEventHistoryPresenter: EventHistoryPresenter

    lateinit var binding: FragmentEventHistoryBinding

    @ProvidePresenter
    fun providePresenter(): EventHistoryPresenter{
        return EventHistoryPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_event_history,
            container,
            false
        )
        binding.presenter = mEventHistoryPresenter
        // Inflate the layout for this fragment
        return binding.root
    }


}
