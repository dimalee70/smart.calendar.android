package kz.smart.calendar.modules.schedule.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.Screens
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.models.db.CategoryDao
import kz.smart.calendar.models.objects.CalendarModel
import kz.smart.calendar.models.objects.Category
import kz.smart.calendar.models.requests.EventsCalendarRequest
import kz.smart.calendar.modules.schedule.view.CalendarView
import javax.inject.Inject

@InjectViewState
class CalendarPresenter : MvpPresenter<CalendarView>() {
    @Inject
    lateinit var client: ApiManager
    @Inject
    lateinit var categoryDao: CategoryDao
    val days: ArrayList<DataDay> = ArrayList()

    init {
        App.appComponent.inject(this)
    }
    private var disposable: Disposable? = null

    fun reloadData(month: Int, monthOffset: Int)
    {
        disposable = client.getEventsCalendar(EventsCalendarRequest(monthOffset))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({  calendar ->
                    days.clear()
                    calendar.data.entries.forEach { it ->
                        val categories: HashMap<Category, Int> = HashMap()

                        val grouping = it.value.groupingBy { it }.eachCount()
                        grouping.forEach { categories[categoryDao.getSync(it.key)] = it.value }

                        val sorted =
                            categories.toList().sortedByDescending { (_, value) -> value }.toMap()

                        days.add(DataDay(it.key.toInt(), month, sorted, sorted.keys.take(3).map { it.color }))
                    }

                    viewState?.setItems(days)
                },
                { error ->
                    run {
                    }
                }
            )

    }
}

data class DataDay(
    val day: Int,
    val month: Int,
    var categories: Map<Category, Int>,
    var topThreeColors: List<String>
)