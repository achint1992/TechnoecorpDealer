package com.technoecorp.gorilladealer.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

@SuppressLint("AppCompatCustomView")
class CircularTextView : TextView {
    private var strokeWidth = 0f
    private var strokeColor: Int = 0
    private var solidColored: Int = 0
    private val circlePaint: Paint = Paint()
    private val strokePaint: Paint = Paint()

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    override fun onDraw(canvas: Canvas?) {
        circlePaint.color = solidColored
        circlePaint.flags = Paint.ANTI_ALIAS_FLAG

        strokePaint.color = strokeColor
        strokePaint.flags = Paint.ANTI_ALIAS_FLAG

        val h = this.height
        val w = this.width

        val diameter = if (h > w) h else w
        val radius = diameter / 2

        this.height = diameter
        this.width = diameter

        canvas!!.drawCircle(
            (diameter / 2).toFloat(), (diameter / 2).toFloat(),
            radius.toFloat(), strokePaint
        )

        canvas.drawCircle(
            (diameter / 2).toFloat(),
            (diameter / 2).toFloat(), radius - strokeWidth, circlePaint
        )
        super.onDraw(canvas)
    }

    fun setSolidColor(color: Int) {
        solidColored = color
    }
}