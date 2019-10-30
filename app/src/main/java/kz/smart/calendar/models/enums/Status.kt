package kz.smart.calendar.models.enums

import com.google.gson.annotations.SerializedName

enum class Status(val value: Int)
{
    @SerializedName("0")
    INACTIVE(0),
    @SerializedName("1")
    ACTIVE(1),
}