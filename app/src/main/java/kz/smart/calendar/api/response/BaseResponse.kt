package kz.smart.calendar.api.response

data class BaseResponse<T>(
    val data: T,
    val status: String?
)