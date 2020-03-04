package com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.core.graphics.component4

/**
 * Created by Gopi Krishna on 2020-01-28.
 */
class CustomConstraintLayout : ConstraintLayout {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        initView()
    }

    private fun initView() {
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        for (i in 1..childCount) {
            val child = getChildAt(i)
            val layoutParams = child.layoutParams as ConstraintLayout.LayoutParams
            val childMeasuredWidth = child.measuredWidth
            val childMeasuredHeight = child.measuredHeight
            val childLeft = left
            var childTop = top

            if(layoutParams.topToBottom != -1){
                childTop = bottom - childMeasuredHeight // do similarly for aligning elements. Just for example real thing is more complex
            }
            child.layout(childLeft, childTop, childLeft + childMeasuredWidth, childTop + childMeasuredHeight)
            // layoutParams.leftMargin // u can play around with margins as well.
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}