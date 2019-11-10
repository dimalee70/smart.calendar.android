package kz.smart.calendar.modules.settings.presentation.registration

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.models.requests.RegisterRequestModel
import kz.smart.calendar.models.shared.DataHolder
import photograd.kz.smart.presentation.view.registration.RegistrationProcessView
import retrofit2.HttpException
import javax.inject.Inject

const val NOT_SET = 0
const val DEFAULT_BIRTHDAY = "10.10.2010"

@InjectViewState
class RegistrationProcessPresenter: MvpPresenter<RegistrationProcessView>() {
    @Inject
    lateinit var client: ApiManager

    private var disposable: Disposable? = null
    val user = RegisterRequestModel("")

    init {
        App.appComponent.inject(this)
    }

    fun register() {
        disposable = client.register(user).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    run {
                        DataHolder.sessionId = result.data.session_id
                        DataHolder.user = result.data.user
                        DataHolder.userId = result.data.user.id
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                        viewState?.showError(error)
                    }
                    if (error is HttpException)
                    {
                        if (error.code() == 405)
                        {
                            logout()
                            user.login = ""
                            user.password = ""
                            user.birthday = ""
                            user.username = ""
                            user.passwordConfirm = ""
                            return@subscribe
                        }
                    }
                }
            )
    }

    fun logout(){
        disposable = client.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {    result ->
                    run {
                        viewState?.hideProgress()
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                    }
                }
            )
    }
}