package kz.smart.calendar.models.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kz.smart.calendar.Constants
import kz.smart.calendar.models.shared.DataHolder
import java.util.*

data class RegisterRequestModel
 (
    var onesignal_player_id: String? = null,
    var platform: Int = Constants.ANDROID_PLATFORM
)
    : BaseObservable()
{
    var login: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.login)
        }

    var password: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    var passwordConfirm: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.passwordConfirm)
        }

    var birthday: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.birthday)
        }

    var gender: Int = 0
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.gender)
        }

    var username: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    var avatar_image_name: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.avatar_image_name)
      }
}