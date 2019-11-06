package kz.smart.calendar.models.objects

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.smart.calendar.modules.poll.domain.VoteOptionPresenter

data class VoteOption(
    val id: Int,
    val text: String,
    val percentage: Float? = null
): BaseObservable() {

//    constructor(id: Int, text:String, percentage: Float) : this(id, text) {
//        this.percentage = percentage
//    }

    var isSlected: Boolean = false
    @Bindable get
    set(value){
        field = value
        notifyChange()
    }

    var presenter: VoteOptionPresenter? = null
}