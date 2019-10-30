package photograd.kz.smart.presentation.presenter.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.common.api.Api
import com.onesignal.OneSignal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.presentation.view.login.LoginInView
import javax.inject.Inject

@InjectViewState
class LoginInPresenter: MvpPresenter<LoginInView>()
{
    @Inject
    lateinit var client: ApiManager

    private var disposable: Disposable? = null

    fun showLogin()
    {
        //viewState.showLogin()
    }

    fun showVideoBackground()
    {
        //viewState.showVideoBackground()
    }

    fun goToHome()
    {
        //viewState.goToHome(isAfterRegistration = false)
    }
    fun showUserAgreement(){
        //viewState.showUserAgreement()
    }
    fun showRestorePassword(height: Int){
        //viewState.showRestorePassword(height)
        //viewState.hideUserAgreement()
    }
}