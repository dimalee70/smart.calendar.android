package kz.smart.calendar.models.objects

import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

data class Event(
    var id: Int,
    var title: String,
    var start_date: Date?,
    var cover_url: String,
    var category_id: Int,
    var address: String,
    var like_amount: Int,
    var attendance: Int,
    var end_date: Date?,
    var organizer: String,
    var organizer_id: Int,
    var subcategory: Subcategory?,
    var option_ids: ArrayList<Int>,
    var friends_participating: ArrayList<User>,
    var is_attending: Boolean,
    var category: Category?
) {
    val firstFriendPhoto: String?
        get() {
            return friends_participating.firstOrNull()?.avatar_url
        }
    val secondFriendPhoto: String?
        get() {
            return friends_participating.drop(1).take(1).lastOrNull()?.avatar_url
        }
    val thirdFriendPhoto: String?
        get() {
            return friends_participating.drop(2).take(1).lastOrNull()?.avatar_url
        }

    val name: String?
        get() {
            return if (subcategory?.title != null) subcategory?.title else category?.title
        }

    val friendNames: String?
        get() {
            return friends_participating.take(3).map { it.username }.joinToString(", ", limit = 3)
        }

    /*val duration: String?
    get()
    {
        return if (start_date != null && end_date != null)
        {
            val days = Days.daysBetween(DateTime(start_date), DateTime(end_date)).days + 1
            val last = days.toString().takeLast(1).toInt()
            val suffix = if (last == 1) "день" else if (last < 5) "дня" else "дней"
            return  "$days $suffix"
        }
        else
        {
            null
        }
    }*/
    var options: ArrayList<Option>? = null

    var eventInfo: EventInfo? = null

    
    fun fromEvent(event: Event)
    {
         id = event.id
         title = event.title
         start_date = event.start_date
         cover_url = event.cover_url
         category_id = event.category_id
         address = event.address
         like_amount = event.like_amount
         attendance = event.attendance
         end_date = event.end_date
         organizer = event.organizer
         organizer_id = event.organizer_id
         subcategory = event.subcategory
         option_ids = event.option_ids
         friends_participating = event.friends_participating
         is_attending = event.is_attending
         category = event.category
         options = event.options
         eventInfo = event.eventInfo
    }
}