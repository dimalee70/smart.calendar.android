package kz.smart.calendar.modules.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.presenter.InjectPresenter
import kz.smart.calendar.R
import kz.smart.calendar.models.shared.DataHolder
import kz.smart.calendar.modules.home.domain.BottomNavigationPresenter
import kz.smart.calendar.modules.home.domain.BottomNavigationView
import kz.smart.calendar.ui.fragment.BaseMvpFragment

/*class BottomNavigationFragment : BaseMvpFragment(), BottomNavigationView {

    companion object {
        const val TAG: String = "BottomNavigationFragment"
        fun newInstance() = BottomNavigationFragment()
    }

    lateinit var binding: FragmentBottomNavigationBinding
    @InjectPresenter
    lateinit var presenter: BottomNavigationPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bottom_navigation,
            container,
            false
        )

        binding.user = DataHolder.user
        binding.presenter = presenter
        return binding.root
    }

}*/
