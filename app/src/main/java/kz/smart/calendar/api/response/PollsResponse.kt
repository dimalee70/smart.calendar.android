package kz.smart.calendar.api.response

import kz.smart.calendar.models.objects.Poll

data class PollsResponse(
    val polls: ArrayList<Poll>
)