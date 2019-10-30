package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.Status

data class Poll(
    val id: Int,
    val title: String,
    val vote_options: ArrayList<VoteOption>,
    val status: Status
)