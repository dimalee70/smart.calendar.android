package kz.smart.calendar.models.objects

import com.google.gson.internal.LinkedTreeMap
import kz.smart.calendar.App
import kz.smart.calendar.models.db.CategoryDao
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CalendarModel(responseMap: LinkedTreeMap<String, ArrayList<Int>>?) {
    @Inject
    lateinit var categoryDao: CategoryDao
    val days: ArrayList<Day> = ArrayList()
    init {
        App.appComponent.inject(this)
        responseMap?.entries?.forEach{ it ->
            val categories: HashMap<Category, Int> = HashMap()

            val grouping = it.value.groupingBy { it }.eachCount()
            grouping.forEach { categories[categoryDao.getSync(it.key)] = it.value }

            val sorted = categories.toList().sortedByDescending { (_, value) -> value}.toMap()

            days.add(Day(it.key.toInt(), sorted, sorted.keys.take(3).map { it.color }))

            var test = days.toString()
        }
    }
}

data class Day(
    val day: Int,
    var categories: Map<Category, Int>,
    var topThreeColors: List<String>
)

enum class DayState {
    PreviousMonth,
    ThisMonth,
    NextMonth
}