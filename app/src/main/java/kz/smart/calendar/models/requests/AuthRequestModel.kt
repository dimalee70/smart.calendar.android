package kz.smart.calendar.models.requests

import kz.smart.calendar.models.shared.DataHolder

data class AuthRequestModel(
    val session_id: String? = DataHolder.sessionId
){

}