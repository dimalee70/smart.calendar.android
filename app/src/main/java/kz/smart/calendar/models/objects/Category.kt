package kz.smart.calendar.models.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kz.smart.calendar.models.enums.Status

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val color: String,
    @ColumnInfo
    val status: Status
)
{
    var checked: Boolean = false
}