package kz.smart.calendar.models.objects

import kz.smart.calendar.models.enums.CompanyType

data class Company(
    val logo_url: String,
    val name: String,
    val company_type: CompanyType
)