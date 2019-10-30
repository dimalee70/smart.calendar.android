package kz.smart.calendar.models.requests

data class EventsForPeriodRequestModel(
    val category_ids: ArrayList<Int>?,
    val subcategory_ids: ArrayList<Int>?,
    val option_ids: ArrayList<Int>?,
    val start_date: String,	//day.month.year
    val end_date: String
)