package kz.smart.calendar.models.objects

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class VoteOption(
    val id: Int,
    val text: String
): BaseObservable() {
    var percentage: Float? = null
    constructor(id: Int, text:String, percentage: Float) : this(id, text) {
        this.percentage = percentage
    }

    var isSlected: Boolean = false
    @Bindable get
    set(value){
        field = value
        notifyChange()
    }
}