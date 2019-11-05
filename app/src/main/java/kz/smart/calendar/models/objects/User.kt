package kz.smart.calendar.models.objects

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.*
import java.util.*

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo
    var id: Int,
    @ColumnInfo
    var username: String?,
    @ColumnInfo
    var gender: Int?,
    @ColumnInfo
    var onesignal_player_id: String?,
    @ColumnInfo
    var role: Int?
) : BaseObservable()
{
    @Ignore
    val avatar_url:	String? = null
}