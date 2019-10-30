package kz.smart.calendar.models.objects

data class EventInfo(
    val event_id: Int,
    val description: String,
    val images: ArrayList<String>,
    val program: ArrayList<ProgramItem>,
    val speakers: ArrayList<Speaker>
)