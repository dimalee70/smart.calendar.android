package kz.smart.calendar.models.objects

data class LoginModel(
    val data: LoginData,
    val status: String
)

data class LoginData(
    val user: User,
    val session_id: String
)