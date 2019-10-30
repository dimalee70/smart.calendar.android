package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.Status

data class Category(
    val id: Int,
    val title: String,
    val color: String,
    val status: Status
)