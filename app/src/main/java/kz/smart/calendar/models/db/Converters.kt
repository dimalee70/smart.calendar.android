package kz.smart.calendar.models.db

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.smart.calendar.models.enums.Status
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    companion object {
        val gson = Gson()

        @TypeConverter
        fun fromString(value: String): ArrayList<String> {
            val listType = object : TypeToken<ArrayList<String>>() {

            }.type

            return gson.fromJson<ArrayList<String>>(value, listType)
        }

        @SuppressLint("NewApi", "SimpleDateFormat")
        private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        @TypeConverter
        @JvmStatic
        fun toOffsetDateTime(value: String?): Date? {
            return value?.let {
                return DateTime.parse(value).toDate()
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @TypeConverter
        @JvmStatic
        fun fromOffsetDateTime(date: Date?): String? {
            return dateFormat.format(date)
        }

        @TypeConverter
        fun fromArrayList(list: ArrayList<String>): String {

            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toDate(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun toLong(value: Date?): Long? {
            return (value?.time)
        }

        @TypeConverter
        @JvmStatic
        fun fromSatus(obj: Status?): String? = obj?.name

        @TypeConverter
        @JvmStatic
        fun toStatus(s: String?): Status? = if (s == null) null else Status.valueOf(s)
    }
}