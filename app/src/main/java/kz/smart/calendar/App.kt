package kz.smart.calendar

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.disposables.Disposable
import kz.smart.calendar.di.components.AppComponent
import kz.smart.calendar.di.components.DaggerAppComponent
import kz.smart.calendar.di.modules.ApplicationModule
import kz.smart.calendar.di.modules.RoomModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber
import com.onesignal.OneSignal
import photograd.kz.photograd.onesignal.NotificationOpenedHandler


class App : MultiDexApplication() {
    private var cicerone: Cicerone<Router>? = null
    private var sAnalytics: FirebaseAnalytics? = null

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    private var disposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()

        instance = this

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationOpenedHandler(NotificationOpenedHandler())
            .init()

        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()

        //if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
        //}

        sAnalytics = FirebaseAnalytics.getInstance(this)
    }

    companion object
    {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
    }
}