package kz.smart.calendar.models.enums

import com.google.gson.annotations.SerializedName

enum class PeopleType(val value: String)
{
    @SerializedName("0")
    SPEAKER("Спикер"),
    @SerializedName("1")
    SPONSOR("Спонсор"),
    @SerializedName("2")
    HEADLINER("Хэдлайнер"),
    @SerializedName("3")
    JURY("Жюри"),
    @SerializedName("4")
    GUEST("Гость"),
    @SerializedName("5")
    PARTNER("Партнер")
}