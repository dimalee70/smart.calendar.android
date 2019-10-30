package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.Status

data class Option(
    val id: Int,
    val title: String,
    val icon_url: String,
    val status: Status
)