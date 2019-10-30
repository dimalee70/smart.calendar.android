package kz.smart.calendar.modules.myevents.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_set_extras.*

import kz.smart.calendar.R

/**
 * A simple [Fragment] subclass.
 */
class SetExtrasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_extras, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_continue.setOnClickListener {
            findNavController().navigate(R.id.action_setExtrasFragment_to_previewActivity)
        }

        btn_participants.setOnClickListener {
            findNavController().navigate(R.id.action_setExtrasFragment_to_setParticipantsFragment)
        }

        btn_schedule.setOnClickListener {
            findNavController().navigate(R.id.action_setExtrasFragment_to_setScheduleFragment)
        }
        btn_partners.setOnClickListener {
            findNavController().navigate(R.id.action_setExtrasFragment_to_setPartnersFragment)
        }
        btn_conditions.setOnClickListener {
            findNavController().navigate(R.id.action_setExtrasFragment_to_setConditionsFragment)
        }
        btn_photogallery.setOnClickListener {
            findNavController().navigate(R.id.action_setExtrasFragment_to_setGalleryFragment)
        }
    }



}
