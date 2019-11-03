package kz.smart.calendar.models.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.smart.calendar.models.enums.Status

@Entity(tableName = "option")
data class Option(
    @PrimaryKey
    @ColumnInfo
    val id: Int,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val icon_url: String,
    @ColumnInfo
    val status: Status
)