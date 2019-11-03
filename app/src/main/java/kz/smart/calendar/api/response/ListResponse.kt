package kz.smart.calendar.api.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @SerializedName("categories",
        alternate = ["options", "subcategories", "events"])
    val items: ArrayList<T>
)