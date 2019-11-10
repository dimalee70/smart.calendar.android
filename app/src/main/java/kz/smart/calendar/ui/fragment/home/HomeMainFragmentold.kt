package kz.smart.calendar.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.library.baseAdapters.BR
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.smart.calendar.App
import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentHomeMainBinding
import kz.smart.calendar.presentation.presenter.home.HomeMainPresenter
import kz.smart.calendar.presentation.view.home.HomeMainView
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment
import kotlinx.android.synthetic.main.activity_home.*
import kz.smart.calendar.models.objects.Event
import ru.terrakok.cicerone.Router
import java.lang.ClassCastException
import javax.inject.Inject

class HomeMainFragment : BaseMvpFragment(), HomeMainView,
    RecyclerBindingAdapter.OnItemClickListener<Event>
{
    override fun setBottomViewVisibility(isVisible: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var position: Int? = null

    companion object {
        const val TAG = "HomeMainFragment"

        fun newInstance(): HomeMainFragment {
            val fragment: HomeMainFragment = HomeMainFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }



    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var mHomeMainPresenter: HomeMainPresenter

    @ProvidePresenter
    fun providePresenter(): HomeMainPresenter{
        return HomeMainPresenter()
    }

    lateinit var binding: FragmentHomeMainBinding

    lateinit var recyclerEventsAdapter: RecyclerBindingAdapter<Event>

    private var onCustomClickListenerRecycler: RecyclerBindingAdapter.OnItemClickListener<Event>? = this

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        recyclerEventsAdapter = RecyclerBindingAdapter(R.layout.event_item, BR.data, context!!)
        if(onCustomClickListenerRecycler != null){
            recyclerEventsAdapter.setOnItemClickListener(onCustomClickListenerRecycler!!)
        }

        mHomeMainPresenter.reloadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_main, container, false)
        binding.presenter = mHomeMainPresenter
        /*activity?.appbar_layout?.visibility = View.VISIBLE
        activity?.pageTv?.visibility = View.VISIBLE
        binding.productsRv.adapter = recyclerEventsAdapter*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onItemClick(position: Int, item: Event) {
        this.position = position
        mHomeMainPresenter.showEvent(item.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            //onCustomClickListenerRecycler = this
        }catch (e: Throwable){
            throw ClassCastException(context.toString())
        }
    }

    override fun onDetach() {
        super.onDetach()
        //onCustomClickListenerRecycler = null

    }

    override fun onResume() {
        super.onResume()
        println("OnResume")
    }
}
