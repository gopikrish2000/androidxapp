package com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.getColor
import java.time.format.DecimalStyle
import java.util.*

/**
 * Created by Gopi Krishna on 2020-01-28.
 */
class FontFitTextView: TextView {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = getColor(R.color.colorPrimaryDark); textSize = 65f; setTextColor(getColor(R.color.colorPrimaryDark))}
    val text = "GopiTextjkklkllllll"

    constructor(context: Context): super(context){initView()}
    constructor(context: Context,attributeSet: AttributeSet): super(context, attributeSet){ initView()}
    constructor(context: Context,attributeSet: AttributeSet, intStyle: Int): super(context, attributeSet,intStyle){ initView()}

    private fun initView() {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var localText = text
        var textWidth = paint.measureText(localText)

        while (textWidth > measuredWidth) {
//            localText = localText.removeRange(localText.lastIndex, localText.lastIndex+1)
//            paint.textSize = paint.textSize - 5f
            textWidth = paint.measureText(localText)
        }
        setMeasuredDimension(textWidth.toInt(), measuredHeight) // Fit a textview to exact size of text.
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, 0f,height/2f + (paint.textSize / 2), paint) // fit text centerVertically in the region.
    }
}