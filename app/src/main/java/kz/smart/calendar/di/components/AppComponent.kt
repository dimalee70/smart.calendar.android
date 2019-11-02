package kz.smart.calendar.di.components

import android.content.Context
import dagger.Component
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.di.ApplicationContext
import kz.smart.calendar.di.CustomApplicationScope
import kz.smart.calendar.di.modules.*
import kz.smart.calendar.models.db.Db
import kz.smart.calendar.models.db.UserDao
import kz.smart.calendar.modules.poll.domain.PollFragment
import kz.smart.calendar.modules.poll.domain.PollPresenter
import kz.smart.calendar.presentation.presenter.LoginInPresenter
import kz.smart.calendar.presentation.presenter.MainAppPresenter
import kz.smart.calendar.presentation.presenter.home.HomeMainPresenter
import kz.smart.calendar.presentation.presenter.home.HomePresenter
import kz.smart.calendar.ui.activity.BaseActivity
import kz.smart.calendar.ui.activity.MainAppActivity
import kz.smart.calendar.ui.activity.home.HomeActivity
import kz.smart.calendar.ui.fragment.home.HomeMainFragment
import photograd.kz.smart.presentation.presenter.login.LoginProcessPresenter
import photograd.kz.smart.presentation.presenter.registration.RegistrationProcessPresenter
import javax.inject.Singleton

@Singleton
@CustomApplicationScope
@Component(modules = [ApplicationModule::class, NavigationModule::class,
    ServiceUtilModule::class, RoomModule::class, EventListModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    @ApplicationContext
    fun instance(): App

    fun getServiceUtil(): ApiManager

    fun glideComponentBuilder(): GlideComponent.Builder

    fun userDao(): UserDao

    fun getDb(): Db

    fun inject(activity: BaseActivity)
    fun inject(activity: MainAppActivity)
    fun inject(activity: HomeActivity)
    fun inject(activity: HomeMainFragment)

    fun inject(fragment: PollFragment)

    fun inject(presenter: MainAppPresenter)
    fun inject(presenter: HomePresenter)
    fun inject(presenter: HomeMainPresenter)
    fun inject(presenter: PollPresenter)
    fun inject(presenter: RegistrationProcessPresenter)
    fun inject(presenter: LoginInPresenter)
    fun inject(presenter: LoginProcessPresenter)

}