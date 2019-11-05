package kz.smart.calendar.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Observable
import kz.smart.calendar.App
import kz.smart.calendar.R
import kz.smart.calendar.Screens
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.api.TokenInterceptor
import kz.smart.calendar.models.db.UserDao
import kz.smart.calendar.models.objects.User
import kz.smart.calendar.models.shared.DataHolder
import kz.smart.calendar.presentation.view.MainAppView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.models.db.CategoryDao
import kz.smart.calendar.models.db.OptionDao
import kz.smart.calendar.models.objects.CalendarModel
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.models.requests.AuthRequestModel
import kz.smart.calendar.models.requests.EventsCalendarRequest
import kz.smart.calendar.models.requests.LoginRequestModel
import kz.smart.calendar.models.shared.Utils
import org.json.JSONObject
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject



@InjectViewState
class MainAppPresenter(private val router: Router) : MvpPresenter<MainAppView>() {
    @Inject
    lateinit var client: ApiManager

    @Inject
    lateinit var userDao: UserDao
    @Inject
    lateinit var optionDao: OptionDao
    @Inject
    lateinit var categoryDao: CategoryDao

    @Inject
    lateinit var tokenInterceptor: TokenInterceptor

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null

    fun showLogin()
    {
        //viewState?.showLogin()
    }

    val user = LoginRequestModel()

    fun auth(event: Event?) {
        viewState?.showProgress()

        disposable = client.auth(AuthRequestModel(DataHolder.sessionId!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {    result ->
                    run {
                        viewState?.hideProgress()
                        DataHolder.user = result.data.user
                        syncData()
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                    }

                    if (error is HttpException)
                    {
                        if (error.code() == 403)
                        {
                            DataHolder.sharedPref.edit().clear().apply()
                            anonimousAuth()
                            return@subscribe
                        }
                    }

                    run {
                        viewState?.showError(error)
                    }
                }
            )
    }

    fun anonimousAuth() {
        viewState?.showProgress()

        disposable = client.anonimousLogin(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {    result ->
                    run {
                        viewState?.hideProgress()
                        DataHolder.user = result.data.user
                        DataHolder.userId = result.data.user.id
                        DataHolder.sessionId = result.data.session_id
                    }
                },
                { error ->
                    run {
                        syncData()
                        viewState?.hideProgress()
                        viewState?.showError(error)

                    }
                }
            )
    }

    private fun syncData()
    {

        disposable = Observables
            .zip(client.getCategories(), client.getOptions())
            { cats, options ->
                categoryDao.deleteAll()
                categoryDao.insertAll(cats.data.items)
                optionDao.deleteAll()
                optionDao.insertAll(options.data.items)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    run {
                        viewState?.hideProgress()
                        router.newRootScreen(Screens.HomeScreen())
                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                        viewState?.showError(error)
                    }
                })
    }

    fun start(event: Event?)
    {
        /*if (DataHolder.isFirstLaunch){
            showTutorial()
            return
        }*/

        if (DataHolder.sessionId == null) {
            anonimousAuth()
        }
        else
        {
            auth(event)
        }
    }
}
