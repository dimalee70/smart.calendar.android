package kz.smart.calendar


object Constants
{
    const val apiEndpoint = BuildConfig.apiEndpoint
    const val version = "1.0.0"
    const val darkTheme = "dark"
    const val jwtPrefsKey = "current_token"
    const val isFirstLaunchKey = "is_first_launch"
    const val userIdPrefsKey = "user_id"
    const val connectTimeout: Long = 180
    const val writeTimeout: Long = 180
    const val readTimeout: Long = 180
    const val progressDelay: Long = 10
    const val termsOfUseLink = "https://photograd.kz/terms-of-use.html"
    const val privacyPolicyLink = "https://photograd.kz/privacy-policy.html"
    const val ANDROID_PLATFORM = 1
}

val rootDestinations = setOf(R.id.feed_dest, R.id.calendar_dest, R.id.poll_dest, R.id.events_dest, R.id.settings_dest)
