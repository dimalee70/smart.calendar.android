package kz.smart.calendar.modules.poll.domain

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.api.response.BaseResponse
import kz.smart.calendar.api.response.PollsResponse
import kz.smart.calendar.events.PollUpdateEvent
import kz.smart.calendar.presentation.presenter.BasePresenter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class PollPresenter(private var router: Router): BasePresenter<PollView>() {

    @Inject
    lateinit var client: ApiManager

    var livePollsResponse = MutableLiveData<BaseResponse<PollsResponse>>()

    init {
        App.appComponent.inject(this)
    }



    fun getPolls(){
        disposables.add(
            client.getPolls()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ result ->
                    run {
                        viewState?.hideLoading()
                    }
                    livePollsResponse.value = result
                },
            {
                error ->
                run {
                    viewState.hideLoading()
                    viewState.showError(error)
                }
            })
        )
    }

    fun observeForPollsResponseBoundary(): MutableLiveData<BaseResponse<PollsResponse>>{
        return livePollsResponse
    }
}