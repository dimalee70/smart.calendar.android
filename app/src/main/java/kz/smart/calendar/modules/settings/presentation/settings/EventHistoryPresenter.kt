package kz.smart.calendar.modules.settings.presentation.settings

import com.arellomobile.mvp.MvpPresenter
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.modules.settings.view.settings.EventHistoryView
import javax.inject.Inject

class EventHistoryPresenter: MvpPresenter<EventHistoryView>(){

    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }
}