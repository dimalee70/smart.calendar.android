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
import kz.smart.calendar.api.response.CategoriesResponce
import kz.smart.calendar.api.response.ListResponse
import kz.smart.calendar.databinding.FragmentCategoriesBinding
import kz.smart.calendar.models.objects.Category
import kz.smart.calendar.modules.settings.presentation.settings.CategoriesPresenter
import kz.smart.calendar.modules.settings.view.settings.CategoriesView
import kz.smart.calendar.ui.adapters.RecyclerBindingAdapter
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/**
 * A simple [Fragment] subclass.
 */
class CategoriesFragment: BaseMvpFragment(), CategoriesView {

    companion object {
        const val TAG = "CategoriesFragment"

        fun newInstance(): CategoriesFragment {
            val fragment: CategoriesFragment =
                CategoriesFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    var categories: ObservableArrayList<Category> = ObservableArrayList()

    lateinit var recyclerBindingAdapter: RecyclerBindingAdapter<Category>

    private val lifecycleRegistry = LifecycleRegistry(this)

    @InjectPresenter
    lateinit var mCategoriesPresenter: CategoriesPresenter

    lateinit var binding: FragmentCategoriesBinding

    @ProvidePresenter
    fun providePresenter(): CategoriesPresenter{
        return CategoriesPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerBindingAdapter = RecyclerBindingAdapter(R.layout.item_categories, BR.data, context!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_categories,
            container,
            false
        )
        binding.presenter = mCategoriesPresenter
        mCategoriesPresenter.attachLifecycle(lifecycleRegistry)
        mCategoriesPresenter.getCategories()
        mCategoriesPresenter.observeCategoryResponseBoundary()
            .observe(this, Observer {
                response -> response.let {
                showCategories(response)
            }
            })
        binding.categoriesRv.adapter = recyclerBindingAdapter
        // Inflate the layout for this fragment
        return binding.root
    }

    fun showCategories(responce: BaseResponse<ListResponse<Category>>){
        categories.addAll(responce.data.items)
        recyclerBindingAdapter.setItems(categories)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun toString(): String {
        return "Категории"
    }

}
