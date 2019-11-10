package kz.smart.calendar.api.response

import kz.smart.calendar.models.objects.Category

data class CategoriesResponce(
    val categories: ArrayList<Category>
)