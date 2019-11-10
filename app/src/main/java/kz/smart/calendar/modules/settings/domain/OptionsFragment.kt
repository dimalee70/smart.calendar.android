package kz.smart.calendar.modules.settings.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kz.smart.calendar.BR

import kz.smart.calendar.R
import kz.smart.calendar.api.response.BaseResponse
import kz.smart.calendar.api.response.ListResponse
import kz.smart.calendar.databinding.FragmentCategoriesBinding
import kz.smart.calendar.databinding.FragmentOptionsBinding
import kz.smart.calendar.models.objects.Category
import kz.smart.calendar.models.objects.Option
import kz.smart.calendar.modules.settings.presentation.settings.CategoriesPresenter
import kz.smart.calendar.modules.settings.presentation.settings.OptionsPresenter
import kz.smart.calendar.modules.settings.view.settings.OptionsView
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class OptionsFragment : BaseMvpFragment(), OptionsView {

    companion object {
        const val TAG = "OptionsFragment"

        fun newInstance(): OptionsFragment {
            val fragment: OptionsFragment =
                OptionsFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    var options: ObservableArrayList<Option> = ObservableArrayList()

    lateinit var recyclerBindingAdapter: RecyclerBindingAdapter<Option>

    private val lifecycleRegistry = LifecycleRegistry(this)

    @InjectPresenter
    lateinit var mOptionsPresenter: OptionsPresenter

    lateinit var binding: FragmentOptionsBinding

    @ProvidePresenter
    fun providePresenter(): CategoriesPresenter {
        return CategoriesPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerBindingAdapter = RecyclerBindingAdapter(R.layout.item_option, BR.data, context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_options,
            container,
            false
        )

        binding.presenter = mOptionsPresenter
        mOptionsPresenter.attachLifecycle(lifecycleRegistry)
        mOptionsPresenter.getOptions()
        mOptionsPresenter.observeOptionsResponseBoundary()
            .observe(this, Observer{
                response -> response.let {
                showOptions(response)
            }
            })
        binding.optionsRv.adapter = recyclerBindingAdapter
        // Inflate the layout for this fragment
        return binding.root
    }

    fun showOptions(response: BaseResponse<ListResponse<Option>>){
        options.addAll(response.data.items)
        recyclerBindingAdapter.setItems(options)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun toString(): String {
        return "Желаемые опции"
    }
}
