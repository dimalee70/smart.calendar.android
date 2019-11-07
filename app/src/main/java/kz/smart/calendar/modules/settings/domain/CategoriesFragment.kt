package kz.smart.calendar.modules.settings.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import kz.smart.calendar.R
import kz.smart.calendar.databinding.FragmentCategoriesBinding
import kz.smart.calendar.modules.settings.presentation.settings.CategoriesPresenter
import kz.smart.calendar.modules.settings.view.settings.CategoriesView
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

    @InjectPresenter
    lateinit var mCategoriesPresenter: CategoriesPresenter

    lateinit var binding: FragmentCategoriesBinding

    @ProvidePresenter
    fun providePresenter(): CategoriesPresenter{
        return CategoriesPresenter()
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
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
