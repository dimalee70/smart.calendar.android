package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.Status
import kz.smart.calendar.modules.poll.domain.PollPresenter
import kz.smart.calendar.modules.poll.domain.VoteOptionPresenter

data class Poll(
    val id: Int,
    val title: String,
    val text:String,
    val vote_options: ArrayList<VoteOption>,
    val status: Status,
    val end_date: String,
    val votes_amount: Int
){
    var presener: VoteOptionPresenter? = null
}