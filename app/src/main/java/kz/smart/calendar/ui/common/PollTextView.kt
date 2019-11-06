package kz.smart.calendar.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.drawable.DrawableCompat
import kz.smart.calendar.R

class PollTextView: AppCompatTextView{
    var mRatio: Float = 0f
//        set(value){
//            field = value
//            invalidate()
//        }

    constructor(context: Context) : super(context) {
        setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet): super(context,attrs){
        setWillNotDraw(false)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle){
        setWillNotDraw(false)
    }

    fun setRatio(ratio: Float){
        mRatio = ratio
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val fill = resources.getDrawable(R.drawable.bg_rounded_votes_selected, null)
        val wrappedDrawable = DrawableCompat.wrap(fill)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor("#5BA1E4"))
        wrappedDrawable.alpha = 128
        wrappedDrawable.setBounds(0,0, (width * mRatio).toInt(), height)
        wrappedDrawable.draw(canvas)
        canvas?.let { fill.draw(it) }
        super.onDraw(canvas)
    }
}
