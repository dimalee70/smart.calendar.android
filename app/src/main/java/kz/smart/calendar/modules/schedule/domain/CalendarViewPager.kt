package kz.smart.calendar.modules.schedule.domain

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import java.util.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View


open class CalendarViewPager(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(
                widthMeasureSpec,
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            val h = child.measuredHeight
            if (h > height) height = h
        }

        var heightMeasureSpec1 = heightMeasureSpec
        if (height != 0) {
            heightMeasureSpec1 = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec1)
    }
}
