package kz.smart.calendar.modules.common.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.models.objects.Event
import javax.inject.Inject

@InjectViewState
class EventDetailsPresenter: MvpPresenter<EventDetailsView>(){
    @Inject
    lateinit var event: Event

    init {
        App.appComponent.inject(this)
    }
}