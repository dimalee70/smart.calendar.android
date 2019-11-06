package kz.smart.calendar.modules.poll.domain

import kz.smart.calendar.models.objects.Poll
import kz.smart.calendar.presentation.BaseView

interface VoteOptionView: BaseView {
    fun setPoll(poll: Poll)
}