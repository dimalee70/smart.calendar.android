package kz.smart.calendar.models.objects

data class Event(
    val id: Int,
    val title: String,
    val start_date: String,
    val cover_url: String,
    val category_id: Int,
    val address: String,
    val like_amount: Int,
    val attendance: Int,
    val end_date: String,
    val organizer: String,
    val organizer_id: Int,
    val subcategory: Subcategory,
    val option_ids: ArrayList<Int>,
    val friends_participating: ArrayList<User>,
    val is_attending: Boolean,
    var category: Category?
)
{
    val firstFriendPhoto: String?
            get() {
                return friends_participating.firstOrNull()?.avatar_url
            }
    val secondFriendPhoto: String?
        get() {
            return friends_participating.drop(1).take(1).lastOrNull()?.avatar_url
        }
    val thirdFriendPhoto: String?
        get() {
            return friends_participating.drop(2).take(1).lastOrNull()?.avatar_url
        }
    var options: ArrayList<Option>? = null
}