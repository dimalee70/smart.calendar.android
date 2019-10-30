package kz.smart.calendar.models.enums

import com.google.gson.annotations.SerializedName

enum class Period(val value: Int)
{
    @SerializedName("0")
    DAY(0),
    @SerializedName("1")
    WEEK(1),
}