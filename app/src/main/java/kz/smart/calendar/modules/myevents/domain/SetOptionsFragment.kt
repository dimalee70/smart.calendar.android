package kz.smart.calendar.modules.myevents.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_set_options.*

import kz.smart.calendar.R

/**
 * A simple [Fragment] subclass.
 */
class SetOptionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_continue.setOnClickListener {
            findNavController().navigate(R.id.action_setOptionsFragment_to_setExtrasFragment)
        }
    }
}
