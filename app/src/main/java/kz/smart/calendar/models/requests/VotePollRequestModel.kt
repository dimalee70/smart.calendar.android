package kz.smart.calendar.models.requests

data class VotePollRequestModel(
    val poll_question_id: Int,
    val vote_option_id: Int
)