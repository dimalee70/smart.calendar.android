package photograd.kz.smart.presentation.presenter.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.onesignal.OneSignal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.models.requests.LoginRequestModel
import kz.smart.calendar.models.shared.DataHolder
import photograd.kz.smart.presentation.view.login.LoginProcessView
import javax.inject.Inject

@InjectViewState
class LoginProcessPresenter : MvpPresenter<LoginProcessView>()
{
    @Inject
    lateinit var client: ApiManager

    private var disposable: Disposable? = null

    val user = LoginRequestModel("",_password = "")

    fun logIn(loginReqModel: LoginRequestModel) {
        /*viewState.showProgress()

        loginReqModel.onesignal_player_id = OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId

        disposable = client.userLogin(loginReqModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {    result ->
                    run {
                        viewState.hideProgress()
                        DataHolder.user = result.data.user
                        DataHolder.userId = result.data.user.id
                        DataHolder.sessionid = result.data.auth.sessionid
                        viewState.showHome()
                    }
                },
                { error ->
                    run {
                        viewState.hideProgress()
                        viewState.showError(error)
                    }
                }
            )*/
    }

    fun registerClick(){
        viewState.onRegisterClick()
    }

    fun forgotPasswordClick(){
        //viewState.onForgotPasswordClick()
    }

    fun loginClick(){
        viewState.onLoginClick()
    }
}