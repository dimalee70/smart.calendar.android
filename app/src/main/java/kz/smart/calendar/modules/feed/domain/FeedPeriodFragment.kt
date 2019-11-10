package kz.smart.calendar.modules.feed.domain


import android.content.Context
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
import java.lang.ClassCastException
import javax.inject.Inject


class FeedPeriodFragment: BaseMvpFragment(), FeedPeriodView,
    RecyclerBindingAdapter.OnItemClickListener<Event>{
    companion object {
        const val PERIOD_EXTRA: String = "PERIOD_EXTRA"

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
    private var onCustomClickListenerRecycler: RecyclerBindingAdapter.OnItemClickListener<Event>? = this

    lateinit var binding: FragmentFeedPeriodBinding

    @Inject
    lateinit var event: Event

    var period: Period? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        eventsAdapter = RecyclerBindingAdapter(R.layout.item_event, BR.data, context!!)
        if(onCustomClickListenerRecycler != null){
            eventsAdapter.setOnItemClickListener(onCustomClickListenerRecycler!!)
        }
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
        mPresenter.getEvents(period!!)
    }

    override fun onItemClick(position: Int, item: Event) {
        event.fromEvent(item)
        EventBus.getDefault().post(OpenEventDetailsEvent(item))
    }

    override fun showEvents(events: ObservableArrayList<Event>) {
        eventsAdapter.setItems(events)
        eventsAdapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onCustomClickListenerRecycler = this
        }catch (e: Throwable){
            throw ClassCastException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        onCustomClickListenerRecycler = null
    }
}
