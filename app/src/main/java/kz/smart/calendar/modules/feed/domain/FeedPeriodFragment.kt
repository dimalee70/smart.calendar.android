package kz.smart.calendar.modules.feed.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.fragment.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_set_extras.*
import kz.smart.calendar.App

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentFeedPeriodBinding
import kz.smart.calendar.events.OpenEventDetailsEvent
import kz.smart.calendar.models.enums.Period
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.modules.feed.presentation.FeedPeriodPresenter
import kz.smart.calendar.modules.feed.presentation.FeedPeriodView
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import org.greenrobot.eventbus.EventBus


class FeedPeriodFragment: BaseMvpFragment(), FeedPeriodView  {
    companion object {
        val PERIOD_EXTRA: String = "PERIOD_EXTRA"

        fun newInstance(period: Period): FeedPeriodFragment {

            val f = FeedPeriodFragment()
            val bdl = Bundle(1)
            bdl.putInt(PERIOD_EXTRA, period.value)
            f.arguments = bdl
            return f

        }
    }

    @InjectPresenter
    lateinit var mPresenter: FeedPeriodPresenter

    @ProvidePresenter
    fun providePresenter(): FeedPeriodPresenter {
        return FeedPeriodPresenter()
    }

    lateinit var eventsAdapter: RecyclerBindingAdapter<Event>

    lateinit var binding: FragmentFeedPeriodBinding

    var period: Period? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        eventsAdapter = RecyclerBindingAdapter(R.layout.item_event, BR.data, context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        period = Period.values()[arguments!!.getInt(PERIOD_EXTRA)]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_period, container, false)
        binding.presenter = mPresenter
        binding.rvEvents.adapter = eventsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*btn_continue.setOnClickListener {
            EventBus.getDefault().post(OpenEventDetailsEvent(null))
        }*/
        mPresenter.getEvents(period!!)
    }

    override fun showEvents(events: ObservableArrayList<Event>) {
        eventsAdapter.setItems(events)
        eventsAdapter.notifyDataSetChanged()
    }
}
