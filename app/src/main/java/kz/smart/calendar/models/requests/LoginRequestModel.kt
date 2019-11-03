package kz.smart.calendar.models.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.smart.calendar.models.shared.DataHolder
import java.util.*

class LoginRequestModel: BaseObservable()
{
    var onesignal_player_id: String? = null
    var platform: Int = 1
    var sessionid: String? = DataHolder.sessionId

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
}