package kz.smart.calendar.ui.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.util.AttributeSet
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class CircleView : View {
    private val paint: Paint = Paint()

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
//        this.color = attrs.getAttributeValue("#000000", "color")
    }
    constructor(context: Context): super(context){
//        this.color = attrs.getAttributeValue("#000000", "color")
    }

    private var color: String = "#000000"

    fun setColor(color: String) {
        this.color = color
        invalidate()
    }

    fun getColor(): Int {
        return color.toInt()
    }

    constructor(context: Context, color: String): this(context) {
        this.color = color
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val x = width
        val y = height
        paint.style = Paint.Style.FILL
        paint.color = Color.TRANSPARENT
        canvas?.drawPaint(paint)
        paint.color = Color.parseColor(color)
        canvas?.drawCircle(x / 2f, y / 2f, width/2f, paint)
    }
}