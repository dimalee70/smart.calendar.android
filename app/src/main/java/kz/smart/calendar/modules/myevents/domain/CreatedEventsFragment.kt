package kz.smart.calendar.modules.myevents.domain


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.smart.calendar.App

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentCreatedEventsBinding
import kz.smart.calendar.events.AddEvent
import kz.smart.calendar.events.OpenEventDetailsEvent
import kz.smart.calendar.events.OpenEventDetailsMyEvents
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.modules.myevents.presentation.CreatedEventsPresenter
import kz.smart.calendar.modules.myevents.presentation.CreatedEventsView
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import org.greenrobot.eventbus.EventBus
import java.lang.ClassCastException
import javax.inject.Inject

class CreatedEventsFragment: BaseMvpFragment(), CreatedEventsView,
    RecyclerBindingAdapter.OnItemClickListener<Event>{
    companion object {
        fun newInstance(): CreatedEventsFragment {
            val f = CreatedEventsFragment()
            val bdl = Bundle(1)
            f.arguments = bdl
            return f

        }
    }

    @InjectPresenter
    lateinit var mPresenter: CreatedEventsPresenter

    @ProvidePresenter
    fun providePresenter(): CreatedEventsPresenter {
        return CreatedEventsPresenter()
    }

    lateinit var eventsAdapter: RecyclerBindingAdapter<Event>
    private var onCustomClickListenerRecycler: RecyclerBindingAdapter.OnItemClickListener<Event>? = this

    lateinit var binding: FragmentCreatedEventsBinding

    @Inject
    lateinit var event: Event

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_created_events, container, false)
        binding.presenter = mPresenter
        binding.rvEvents.adapter = eventsAdapter
        binding.createEvent.setOnClickListener {
            EventBus.getDefault().post(AddEvent())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.getEvents()
    }

    override fun onItemClick(position: Int, item: Event) {
        event.fromEvent(item)
        EventBus.getDefault().post(OpenEventDetailsMyEvents(item))
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