package kz.smart.calendar.onesignal

import androidx.core.app.NotificationCompat
import android.util.Log
import com.onesignal.NotificationExtenderService
import com.onesignal.OSNotificationReceivedResult
import java.math.BigInteger


class NotificationService : NotificationExtenderService() {
    override fun onNotificationProcessing(receivedResult: OSNotificationReceivedResult): Boolean {

        Log.d("NotificationService","new notification")
        if (receivedResult.payload?.additionalData == null)
        {
            showNotification()
            return true
        }
        if (!receivedResult.isAppInFocus) {
            showNotification()
        }

        return true
    }

    private fun showNotification()
    {
        val overrideSettings = OverrideSettings()
        overrideSettings.extender = NotificationCompat.Extender { builder ->
            builder.setColor(BigInteger("4696f2", 16).toInt())
        }

        val displayedResult = displayNotification(overrideSettings)
    }
}