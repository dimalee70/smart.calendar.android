package kz.smart.calendar.modules.common.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import kz.smart.calendar.R
import kz.smart.calendar.ui.common.BackButtonListener

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : Fragment(), BackButtonListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onBackPressed(): Boolean{
        findNavController().popBackStack()
        return true
    }
}
