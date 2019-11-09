package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.PeopleType

data class People(
    val avatar_url: String,
    val name: String,
    val title: String,
    val people_type: PeopleType
)