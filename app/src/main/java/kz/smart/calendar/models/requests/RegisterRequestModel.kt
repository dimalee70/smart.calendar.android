package kz.smart.calendar.models.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
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
        }

    var password: String = ""
        @Bindable get
        set(value) {
            field = value
        }

    var passwordConfirm: String = ""
        @Bindable get
        set(value) {
            field = value
        }

    var birthday: String = ""
        @Bindable get
        set(value) {
            field = value
        }

    var gender: Int = 0
        @Bindable get
        set(value) {
            field = value
        }

    var username: String = ""
        @Bindable get
        set(value) {
            field = value
        }

    var avatar_image_name: String = ""
        @Bindable get
        set(value) {
            field = value
      }
}