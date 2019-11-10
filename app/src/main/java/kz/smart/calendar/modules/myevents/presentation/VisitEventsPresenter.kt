package kz.smart.calendar.modules.myevents.presentation

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.models.db.CategoryDao
import kz.smart.calendar.models.db.OptionDao
import kz.smart.calendar.models.enums.Period
import kz.smart.calendar.models.enums.Status
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.models.objects.Option
import kz.smart.calendar.models.requests.FeedRequestModel
import kz.smart.calendar.models.requests.StatusRequestModel
import javax.inject.Inject
import kotlin.collections.ArrayList

@InjectViewState
class VisitEventsPresenter : MvpPresenter<VisitEventsView>() {
    @Inject
    lateinit var client: ApiManager
    @Inject
    lateinit var categoryDao: CategoryDao
    @Inject
    lateinit var optionDao: OptionDao

    init {
        App.appComponent.inject(this)
    }

    val count: ObservableInt = ObservableInt(0)

    private var disposable: Disposable? = null

    fun getEvents()
    {
        disposable = client.getAttendingEvents(StatusRequestModel(Status.ACTIVE.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({  eventsResp ->
                val events = ObservableArrayList<Event>()
                eventsResp.data.items.forEach {
                    it.category = categoryDao.getSync(it.category_id)
                    it.options = ArrayList<Option>()
                    it.option_ids.forEach {opt ->
                        it.options?.add(optionDao.getSync(opt))
                    }
                }
                events.addAll(eventsResp.data.items)
                run {
                    count.set(eventsResp.data.items.size)
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
