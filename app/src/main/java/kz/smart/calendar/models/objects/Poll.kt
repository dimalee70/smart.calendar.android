package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.Status

data class Poll(
    val id: Int,
    val title: String,
    val text:String,
    val votesAmount: Int,
    val vote_options: ArrayList<VoteOption>,
    val status: Status,
    val end_date: String,
    val votes_amount: Int
)