package kz.smart.calendar.models.objects

data class LoginData(
    val user: User,
    val session_id: String
)