package kz.smart.calendar.models.shared


import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.smart.calendar.App
import kz.smart.calendar.Constants
import kz.smart.calendar.models.objects.User
import javax.inject.Inject


object DataHolder : BaseObservable()
{
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(App.appComponent.context())

    var user: User? = null
    @Bindable get

    var userId: Int
    set(value) {
        sharedPref.edit().putInt(Constants.userIdPrefsKey, value).apply()
    }
    get() {
        return sharedPref.getInt(Constants.userIdPrefsKey, -1)
    }

    var sessionId: String?
    set(value) {
        sharedPref.edit().putString(Constants.jwtPrefsKey, value).apply()
    }
    get() {
        return sharedPref.getString(Constants.jwtPrefsKey, null)
    }

    var isFirstLaunch: Boolean
        set(value) {
            sharedPref.edit().putBoolean(Constants.isFirstLaunchKey, value).apply()
        }
        get() {
            return sharedPref.getBoolean(Constants.isFirstLaunchKey, true)
        }
}