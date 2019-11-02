package kz.smart.calendar.api

import kz.smart.calendar.api.response.*
import io.reactivex.Observable
import kz.smart.calendar.models.objects.*
import kz.smart.calendar.models.requests.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiManager {
    @POST("anonymous_auth")
    fun anonimousLogin(@Body body: LoginRequestModel): Observable<LoginModel>

    @POST("login")
    fun userLogin(@Body body: LoginRequestModel): Observable<LoginModel>

    @POST("auth")
    fun auth(@Body body: AuthRequestModel): Observable<LoginModel>

    @POST("registration")
    fun register(@Body body: RegisterRequestModel): Observable<LoginModel>

    @POST("logout")
    fun logout(): Observable<StatusResponse>

    @POST("get_options")
    fun getOptions(@Body body: StatusRequestModel): Observable<ListResponse<Option>>

    @POST("get_categories")
    fun getCategories(@Body body: StatusRequestModel): Observable<ListResponse<Category>>

    @POST("get_subcategories")
    fun getSubcategories(@Body body: SubRequestModel): Observable<ListResponse<Subcategory>>

    @POST("user_feed")
    fun getFeed(@Body body: FeedRequestModel): Observable<ListResponse<Event>>

    @POST("get_events_for_period")
    fun getEventsForPeriod(@Body body: EventsForPeriodRequestModel): Observable<ListResponse<Event>>

    @POST("user_will_attend_event")
    fun willAttend(@Body body: WillAttendRequest): Observable<StatusResponse>

    @POST("get_attending_events")
    fun getAttendingEvents(@Body body: StatusRequestModel): Observable<ListResponse<Event>>

    @POST("get_created_events")
    fun getCreatedEvents(@Body body: StatusRequestModel): Observable<ListResponse<Event>>

    @POST("change_event_status")
    fun changeEventStatus(@Body body: ChangeEventStatusRequestModel): Observable<Event>

    @POST("like_event")
    fun likeEvent(@Body body: LikeEventRequestModel): Observable<CurrentStateResponse>

    @POST("upload_image")
    fun uploadImage(@Body body: ImageRequestModel): Observable<ImageResponse>
}