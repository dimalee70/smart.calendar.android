package kz.smart.calendar.models.objects

data class ProgramItem(
    val start: String,
    val end: String,
    val text: String,
    val order_number: Int
)