package kz.smart.calendar.models.requests

import kz.smart.calendar.models.enums.Status

data class ChangeEventStatusRequestModel(
    val event_id: Int,
    val status: Status
)