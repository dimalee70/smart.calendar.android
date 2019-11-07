package kz.smart.calendar.modules.settings.presentation.settings

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.disposables.Disposable
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.modules.settings.view.settings.SubscriptionsView
import javax.inject.Inject

@InjectViewState
class SubscriptionsPresenter: MvpPresenter<SubscriptionsView>(){
    @Inject
    lateinit var client: ApiManager

    private var disposable: Disposable? = null

    init {
        App.appComponent.inject(this)
    }
}