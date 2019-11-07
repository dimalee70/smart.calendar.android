package kz.smart.calendar.modules.poll.domain

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kz.smart.calendar.App
import kz.smart.calendar.api.ApiManager
import kz.smart.calendar.events.PollUpdateEvent
import kz.smart.calendar.models.objects.Poll
import kz.smart.calendar.models.objects.VoteOption
import kz.smart.calendar.models.requests.VotePollRequestModel
import kz.smart.calendar.presentation.presenter.BasePresenter
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
import javax.inject.Inject

@InjectViewState
class VoteOptionPresenter(private var poll: Poll, private val vote_options: ArrayList<VoteOption>): BasePresenter<VoteOptionView>() {

    @Inject
    lateinit var client: ApiManager

    init {
        App.appComponent.inject(this)
    }

    private var disposable: Disposable? = null

    fun sendOption(){
        val vote = vote_options.find {
            it.isSlected
        }
        if (vote != null){
            vote.isSlected = false
//            vote.percentage = 50f
//            vote.isSlected = false
//            val idx = poll.vote_options.indexOfFirst {
//                it.id == vote.id
//            }
//
//            poll.vote_options[idx] = vote
//            EventBus.getDefault().post(PollUpdateEvent(poll))

        disposable = client.sendVote(VotePollRequestModel(poll.id, vote.id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {    result ->
                    run {
                        viewState?.hideProgress()
                        val p = result.data.poll
                        EventBus.getDefault().post(PollUpdateEvent(p))

                    }
                },
                { error ->
                    run {
                        viewState?.hideProgress()
                    }

                    if (error is HttpException)
                    {
                        if (error.code() == 403)
                        {
                            return@subscribe
                        }
                        else
                        {
                        }
                    }

                    run {
                        viewState?.showError(error)
                    }
                }
            )
        }
//        println(pollId)
//        println(vote_options)
//        println("Click Send")
    }

}