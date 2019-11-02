package kz.smart.calendar.models.objects

data class VoteOption(
    val id: Int,
    val text: String
){
    var percentage: Float? = null
    constructor(id: Int, text:String, percentage: Float) : this(id, text) {
        this.percentage = percentage
    }
}