package kz.smart.calendar.di.components

import android.content.Context
import dagger.Component
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.di.ApplicationContext
import kz.smart.calendar.di.CustomApplicationScope
import kz.smart.calendar.di.modules.*
import kz.smart.calendar.models.db.CategoryDao
import kz.smart.calendar.models.db.Db
import kz.smart.calendar.models.db.OptionDao
import kz.smart.calendar.models.db.UserDao
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.modules.common.presentation.EventDetailsPresenter
import kz.smart.calendar.modules.feed.domain.FeedPeriodFragment
import kz.smart.calendar.modules.feed.presentation.FeedPeriodPresenter
import kz.smart.calendar.modules.myevents.domain.CreatedEventsFragment
import kz.smart.calendar.modules.myevents.domain.VisitEventsFragment
import kz.smart.calendar.modules.myevents.presentation.CreatedEventsPresenter
import kz.smart.calendar.modules.myevents.presentation.VisitEventsPresenter
import kz.smart.calendar.modules.poll.domain.PollFragment
import kz.smart.calendar.modules.poll.domain.PollPresenter
import kz.smart.calendar.modules.poll.domain.VoteOptionPresenter
import kz.smart.calendar.modules.schedule.domain.ScheduleFragment
import kz.smart.calendar.modules.schedule.presentation.CalendarPresenter
import kz.smart.calendar.modules.schedule.presentation.SchedulePresenter
import kz.smart.calendar.modules.settings.presentation.login.LoginInPresenter
import kz.smart.calendar.presentation.presenter.MainAppPresenter
import kz.smart.calendar.presentation.presenter.home.HomeMainPresenter
import kz.smart.calendar.presentation.presenter.home.HomePresenter
import kz.smart.calendar.ui.activity.BaseActivity
import kz.smart.calendar.ui.activity.MainAppActivity
import kz.smart.calendar.ui.activity.home.HomeActivity
import kz.smart.calendar.ui.fragment.home.HomeMainFragment
import kz.smart.calendar.modules.settings.presentation.login.LoginProcessPresenter
import kz.smart.calendar.modules.settings.presentation.registration.RegistrationProcessPresenter
import javax.inject.Singleton

@Singleton
@CustomApplicationScope
@Component(modules = [ApplicationModule::class, NavigationModule::class,
    ServiceUtilModule::class, RoomModule::class, EventModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    @ApplicationContext
    fun instance(): App

    fun getServiceUtil(): ApiManager

    fun glideComponentBuilder(): GlideComponent.Builder

    fun userDao(): UserDao
    fun optionDao(): OptionDao
    fun categoryDao(): CategoryDao
    fun event(): Event

    fun getDb(): Db

    fun inject(activity: BaseActivity)
    fun inject(activity: MainAppActivity)
    fun inject(activity: HomeActivity)
    fun inject(fragment: HomeMainFragment)

    fun inject(fragment: PollFragment)
    fun inject(fragment: ScheduleFragment)
    fun inject(fragment: FeedPeriodFragment)
    fun inject(fragment: VisitEventsFragment)
    fun inject(fragment: CreatedEventsFragment)

    fun inject(presenter: MainAppPresenter)
    fun inject(presenter: HomePresenter)
    fun inject(presenter: HomeMainPresenter)
    fun inject(presenter: PollPresenter)
    fun inject(presenter: RegistrationProcessPresenter)
    fun inject(presenter: LoginInPresenter)
    fun inject(presenter: LoginProcessPresenter)
    fun inject(presenter: VoteOptionPresenter)
    fun inject(presenter: CalendarPresenter)
    fun inject(presenter: SchedulePresenter)
    fun inject(presenter: FeedPeriodPresenter)
    fun inject(presenter: EventDetailsPresenter)
    fun inject(presenter: VisitEventsPresenter)
    fun inject(presenter: CreatedEventsPresenter)
}