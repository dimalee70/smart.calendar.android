package kz.smart.calendar.models.enums

import com.google.gson.annotations.SerializedName

enum class CompanyType(val value: String)
{
    @SerializedName("0")
    PARTNER("Партнер"),
    @SerializedName("1")
    SPONSOR("Спонсор"),
    @SerializedName("2")
    PRIVATE_SPONSOR("Частный спонсор"),
    @SerializedName("3")
    INDO_PARTNER("Инфопартнер")
}