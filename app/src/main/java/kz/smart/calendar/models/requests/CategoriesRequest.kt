package kz.smart.calendar.models.requests

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class CategoriesRequest: BaseObservable() {

    var id: Int? = null

    var status: Int? = null

    var color: String = ""
    @Bindable get
    set(value){
        field = value
    }

    var title: String = ""
    @Bindable
    set(value){
        field = value
    }
}