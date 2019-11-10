package photograd.kz.photograd.onesignal

import android.content.Intent
import android.net.Uri
import com.onesignal.OSNotificationAction
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal
import io.reactivex.disposables.Disposable
import kz.smart.calendar.App
import kz.smart.calendar.ui.activity.MainAppActivity


internal class NotificationOpenedHandler : OneSignal.NotificationOpenedHandler {
    private var disposable: Disposable? = null

    override fun notificationOpened(result: OSNotificationOpenResult) {
        val actionType = result.action.type

        if (actionType == OSNotificationAction.ActionType.Opened)
        {
            if (!result.notification.payload?.launchURL.isNullOrEmpty())
            {
                startBrowser(result.notification.payload!!.launchURL)
                return
            }


            if (result.notification.payload?.additionalData == null)
            {
                startMain()
                return
            }
        }
    }

    private fun startMain()
    {
        val intent = Intent(App.appComponent.context(), MainAppActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
        App.appComponent.context().startActivity(intent)
    }

    private fun startBrowser(url: String)
    {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse(url)
        App.appComponent.context().startActivity(intent)
    }
}