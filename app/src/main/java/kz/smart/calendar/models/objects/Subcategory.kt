package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.Status

data class Subcategory(
    val id: Int,
    val title: String,
    val status: Status,
    val category_id: Int
)