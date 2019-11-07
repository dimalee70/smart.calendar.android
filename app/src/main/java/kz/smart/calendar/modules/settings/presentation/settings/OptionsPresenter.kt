package kz.smart.calendar.modules.settings.presentation.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.modules.settings.view.settings.OptionsView
import javax.inject.Inject

@InjectViewState
class OptionsPresenter: MvpPresenter<OptionsView>() {

    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }
}