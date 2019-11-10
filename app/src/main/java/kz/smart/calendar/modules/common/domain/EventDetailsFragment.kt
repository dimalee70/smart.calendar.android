package kz.smart.calendar.modules.common.domain


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_event_details.*

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentEventDetailsBinding
import kz.smart.calendar.models.shared.Utils
import kz.smart.calendar.modules.common.presentation.EventDetailsPresenter
import kz.smart.calendar.modules.common.presentation.EventDetailsView
import kz.smart.calendar.ui.activity.home.HomeActivity
import kz.smart.calendar.ui.fragment.BaseMvpFragment


class EventDetailsFragment : BaseMvpFragment(), EventDetailsView {

    companion object {
        fun newInstance(): EventDetailsFragment {
            val f = EventDetailsFragment()
            val bdl = Bundle(1)
            f.arguments = bdl
            return f

        }
    }
    @InjectPresenter
    lateinit var mPresenter: EventDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): EventDetailsPresenter {
        return EventDetailsPresenter()
    }

    lateinit var binding: FragmentEventDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false)
        binding.presenter = mPresenter
        binding.data = mPresenter.event
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.getEventInfo()

        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
