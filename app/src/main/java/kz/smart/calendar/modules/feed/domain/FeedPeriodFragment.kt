package kz.smart.calendar.modules.feed.domain


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kz.smart.calendar.R
import kz.smart.calendar.models.enums.Period


class FeedPeriodFragment : Fragment() {
    companion object {
        val PERIOD_EXTRA: String = "PERIOD_EXTRA"

        fun newInstance(period: Period): FeedPeriodFragment {

            val f = FeedPeriodFragment()
            val bdl = Bundle(1)
            bdl.putInt(PERIOD_EXTRA, period.value)
            f.arguments = bdl
            return f

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val period = arguments!!.getInt(PERIOD_EXTRA)

        return inflater.inflate(R.layout.fragment_feed_period, container, false)
    }


}
