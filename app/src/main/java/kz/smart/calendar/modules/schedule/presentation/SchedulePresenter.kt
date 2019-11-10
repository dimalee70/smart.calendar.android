package kz.smart.calendar.modules.schedule.presentation

import androidx.databinding.ObservableArrayList
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.events.CalendarCellChosenEvent
import kz.smart.calendar.events.CalendarDefaultCellEvent
import kz.smart.calendar.models.db.CategoryDao
import kz.smart.calendar.models.db.OptionDao
import kz.smart.calendar.models.objects.Category
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.models.objects.Option
import kz.smart.calendar.models.requests.EventsDayRequest
import kz.smart.calendar.modules.schedule.domain.Day
import kz.smart.calendar.modules.schedule.view.ScheduleView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@InjectViewState
class SchedulePresenter : MvpPresenter<ScheduleView>() {
    @Inject
    lateinit var client: ApiManager
    @Inject
    lateinit var categoryDao: CategoryDao
    @Inject
    lateinit var optionDao: OptionDao

    init {
        App.appComponent.inject(this)
    }

    var currentDay: Day? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: CalendarCellChosenEvent) {
        currentDay = event.day
        viewState?.showSelectedDate(event.day)
        if (event.day.dataDay?.categories?.isNotEmpty() == true)
        {
            getEventsByDay(event.day)
        }
        else{
            viewState?.showEvents(ObservableArrayList<Event>())
        }
    }
    private var disposable: Disposable? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: CalendarDefaultCellEvent) {
        if (currentDay == null)
        {
            onMessageEvent(CalendarCellChosenEvent(event.day))
        }
    }

    fun getEventsByDay(day: Day)
    {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        val date = formatter.format(day.calendar.time)

        disposable = client.getEventsForDay(EventsDayRequest(date))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({  calendar ->
                val events = ObservableArrayList<Event>()
                calendar.data.items.forEach {
                    it.category = categoryDao.getSync(it.category_id)
                    it.options = ArrayList<Option>()
                    it.option_ids.forEach {opt ->
                        it.options?.add(optionDao.getSync(opt))
                    }
                }
                events.addAll(calendar.data.items)
                run {
                    viewState?.showEvents(events)
                }
            },
            { error ->
                    run {
                    }
                }
            )
    }
}
