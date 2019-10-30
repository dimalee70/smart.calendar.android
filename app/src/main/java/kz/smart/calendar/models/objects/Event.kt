package kz.smart.calendar.models.objects

data class Event(
    val id: Int,
    val title: String,
    val start_date: String,
    val cover_url: String,
    val category_id: Int,
    val address: String,
    val like_amount: Int,
    val attendance: Int
)