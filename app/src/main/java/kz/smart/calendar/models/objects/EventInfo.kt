package kz.smart.calendar.models.objects

data class EventInfo(
    val event_id: Int,
    val description: String,
    val gallery: ArrayList<String>,
    val terms: ArrayList<String>,
    val program: ArrayList<ProgramItem>,
    val speakers: ArrayList<Speaker>,
    val note: String,
    val schedule: ArrayList<ScheduleItem>,
    val guests: ArrayList<People>,
    val companies: ArrayList<Company>

)