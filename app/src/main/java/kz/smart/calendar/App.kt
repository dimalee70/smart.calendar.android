package kz.smart.calendar

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.di.components.AppComponent
import kz.smart.calendar.di.components.DaggerAppComponent
import kz.smart.calendar.di.modules.ApplicationModule
import kz.smart.calendar.di.modules.RoomModule
import io.fabric.sdk.android.Fabric
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber


class App : MultiDexApplication() {
    private var cicerone: Cicerone<Router>? = null

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    private var disposable: Disposable? = null

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .roomModule(RoomModule(this))
            .build()


        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()
        Fabric.with(this, crashlyticsKit)

        //if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
        //}
    }

    companion object
    {
        lateinit var appComponent: AppComponent
        lateinit var instance: App
    }
}