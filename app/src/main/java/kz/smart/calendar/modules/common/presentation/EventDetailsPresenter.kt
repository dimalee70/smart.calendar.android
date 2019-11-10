package kz.smart.calendar.modules.common.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.models.requests.LikeEventRequestModel
import javax.inject.Inject

@InjectViewState
class EventDetailsPresenter: MvpPresenter<EventDetailsView>(){
    @Inject
    lateinit var event: Event

    private var disposable: Disposable? = null

    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    fun getEventInfo()
    {
        if (event.eventInfo != null)
        {
            return
        }

        disposable = client.getEventInfo(LikeEventRequestModel(event.id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({  eventInfoResp ->
                event.eventInfo = eventInfoResp.data
            },
                { error ->
                    run {
                    }
                }
            )
    }
}