package kz.smart.calendar.modules.poll.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.smart.calendar.App
import kz.smart.calendar.BR

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentPollBinding
import kz.smart.calendar.models.enums.Status
import kz.smart.calendar.models.objects.Event
import kz.smart.calendar.models.objects.Poll
import kz.smart.calendar.models.objects.VoteOption
import kz.smart.calendar.presentation.presenter.home.HomeMainPresenter
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PollFragment : BaseMvpFragment(), PollView{

    companion object {
        const val TAG = "HomeMainFragment"

        fun newInstance(): PollFragment {
            val fragment: PollFragment = PollFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    var polls: ObservableArrayList<Poll> = ObservableArrayList()

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mPollPresenter: PollPresenter

    @ProvidePresenter
    fun providePresenter(): PollPresenter {
        return PollPresenter(router)
    }

    lateinit var recyclerPollAdapter: RecyclerBindingAdapter<Poll>

    lateinit var binding: FragmentPollBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        recyclerPollAdapter = RecyclerBindingAdapter(R.layout.item_poll, BR.data, context!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_poll, container, false)
        // Inflate the layout for this fragment

        val votes = ArrayList<VoteOption>()
        votes.add(VoteOption(1,"Скриптонит", 28f))
        votes.add(VoteOption(2,"Скриптонит1"))
        votes.add(VoteOption(3,"Скриптонит2"))
        votes.add(VoteOption(4,"амв"))
        votes.add(VoteOption(5,"амв"))
        votes.add(VoteOption(6,"вмам"))
        votes.add(VoteOption(7,"авамв"))

        val pollList = ArrayList<Poll>()
        pollList.add(Poll(1, "Празднование дня города", "Кого пригласить на выступление во время праздника?", "5 дней", 388, votes, Status.ACTIVE,"", 20))
        pollList.add(Poll(2, "Празднование дня города2", "Кого пригласить на выступление во время праздника?2", "10 дней", 500, votes, Status.INACTIVE,"", 20))
        pollList.add(Poll(3, "Празднование дня города3", "Кого пригласить на выступление во время праздника?3", "20 дней", 600, votes, Status.ACTIVE,"", 20))
        pollList.add(Poll(4, "Празднование дня города4", "Кого пригласить на выступление во время праздника?4", "25 дней", 700, votes, Status.INACTIVE,"", 20))
        pollList.add(Poll(5, "Празднование дня города5", "Кого пригласить на выступление во время праздника?5", "35 дней", 800, votes, Status.ACTIVE,"", 20))

        polls.addAll(pollList)
        recyclerPollAdapter.setItems(polls)
        binding.pollRv.adapter = recyclerPollAdapter
        binding.pollRv.setHasFixedSize(true)
        return binding.root
    }


}
