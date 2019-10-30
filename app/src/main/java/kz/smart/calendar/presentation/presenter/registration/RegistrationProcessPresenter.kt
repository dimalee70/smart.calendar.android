package photograd.kz.smart.presentation.presenter.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.onesignal.OneSignal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.Constants
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.models.shared.DataHolder
import photograd.kz.smart.presentation.view.registration.RegistrationProcessView
import javax.inject.Inject

const val NOT_SET = 0
const val DEFAULT_BIRTHDAY = "10.10.2010"

@InjectViewState
class RegistrationProcessPresenter: MvpPresenter<RegistrationProcessView>() {
    @Inject
    lateinit var client: ApiManager

    private var disposable: Disposable? = null

    fun register(
        email: String,
        username: String,
        password: String
    ) {

        /*val requestModel = RegistrationRequestModel(
            email = email,
            username = username,
            password = password,
            onesignal_player_id = OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId,
            platform = Constants.ANDROID_PLATFORM,
            gender = NOT_SET,
            birthday = DEFAULT_BIRTHDAY

        )
        disposable = client.register(model = requestModel).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        DataHolder.sessionId = result.data.auth.sessionid
                        DataHolder.user = result.data.user
                        DataHolder.userId = result.data.user.id
                        viewState.hideProgress()
                        viewState.showHome()
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                        viewState?.showError(error)
                    }
                }
            )*/
    }

    fun register() {
        viewState.onRegister()
    }
}