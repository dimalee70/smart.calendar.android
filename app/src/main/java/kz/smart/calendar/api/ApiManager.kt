package kz.smart.calendar.api

import com.google.gson.internal.LinkedTreeMap
import kz.smart.calendar.api.response.*
import io.reactivex.Observable
import kz.smart.calendar.models.objects.*
import kz.smart.calendar.models.requests.*
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiManager {
    @POST("anonymous_auth")
    fun anonimousLogin(@Body body: LoginRequestModel): Observable<BaseResponse<LoginData>>

    @POST("login")
    fun userLogin(@Body body: LoginRequestModel): Observable<BaseResponse<LoginData>>

    @POST("auth")
    fun auth(@Body body: AuthRequestModel): Observable<BaseResponse<LoginData>>

    @POST("registration")
    fun register(@Body body: RegisterRequestModel): Observable<BaseResponse<LoginData>>

    @POST("logout")
    fun logout(): Observable<BaseResponse<StatusResponse>>

    @POST("get_options")
    fun getOptions(): Observable<BaseResponse<ListResponse<Option>>>

    @POST("get_categories")
    fun getCategories(): Observable<BaseResponse<ListResponse<Category>>>

    @POST("get_subcategories")
    fun getSubcategories(@Body body: SubRequestModel): Observable<BaseResponse<ListResponse<Subcategory>>>

    @POST("user_feed")
    fun getFeed(@Body body: FeedRequestModel): Observable<BaseResponse<ListResponse<Event>>>

    @POST("get_events_for_period")
    fun getEventsForPeriod(@Body body: EventsForPeriodRequestModel): Observable<BaseResponse<ListResponse<Event>>>

    @POST("user_will_attend_event")
    fun willAttend(@Body body: WillAttendRequest): Observable<BaseResponse<StatusResponse>>

    @POST("get_attending_events")
    fun getAttendingEvents(@Body body: StatusRequestModel): Observable<BaseResponse<ListResponse<Event>>>

    @POST("get_created_events")
    fun getCreatedEvents(@Body body: StatusRequestModel): Observable<BaseResponse<ListResponse<Event>>>

    @POST("change_event_status")
    fun changeEventStatus(@Body body: ChangeEventStatusRequestModel): Observable<BaseResponse<Event>>

    @POST("like_event")
    fun likeEvent(@Body body: LikeEventRequestModel): Observable<BaseResponse<CurrentStateResponse>>

    @POST("upload_image")
    fun uploadImage(@Body body: ImageRequestModel): Observable<BaseResponse<ImageResponse>>

    @POST("get_polls")
    fun getPolls(): Observable<BaseResponse<PollsResponse>>

    @POST("get_events_calendar")
    fun getEventsCalendar(@Body body: EventsCalendarRequest): Observable<BaseResponse<LinkedTreeMap<String, ArrayList<Int>>>>

    @POST("get_events_for_day")
    fun getEventsForDay(@Body body: EventsDayRequest): Observable<BaseResponse<ListResponse<Event>>>


    @POST("poll_vote")
    fun sendVote(@Body body: VotePollRequestModel):Observable<BaseResponse<Poll>>
}