package com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.MyApplication


/**
 * Created by Gopi Krishna on 2020-01-23.
 */
class CustomTextView : TextView {

    lateinit var paint:Paint

    constructor(viewContext: Context): super(viewContext) { initView()}

    constructor(viewContext: Context, attributeSet: AttributeSet?): super(viewContext,attributeSet){
        initView()
    }

    private fun initView() {
        paint = Paint()
        paint.color = ContextCompat.getColor(MyApplication.getInstance(), com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.color.colorAccent)
//        paint.alpha = 123
        paint.strokeWidth = 25f // width of lines/edges...
        paint.isAntiAlias  = true // for smooth edges, it make edges blend to background by changing alpha gradually, So looks sharp
        paint.style = Paint.Style.STROKE // will only draw stoke , there is fill mode & stroke N Fill mode as well & Fill is default
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        println("CustomTextView.onLayout")

        (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var myWidth = measuredWidth
        var myHeight = measuredHeight
        val leftMargin = (layoutParams as ViewGroup.MarginLayoutParams).leftMargin // u can do something with margin as well if u want.


        var widthWithoutPadding = measuredWidth - paddingLeft - paddingRight
        var heightWithoutPadding = measuredHeight - paddingTop - paddingBottom

        val RATIO = 4f/3f
        val maxWidth = (heightWithoutPadding * RATIO).toInt()
        val maxHeight = (widthWithoutPadding / RATIO).toInt()

        if (widthWithoutPadding > maxWidth) {
            myWidth = maxWidth + paddingLeft + paddingRight
        } else {
            myHeight = maxHeight + paddingTop + paddingBottom
        }
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = getMeasuredWidth();
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = getMeasuredHeight();
        }
        setMeasuredDimension(myWidth, myHeight)

        /*if(widthWithoutPadding > heightMeasureSpec){
            widthWithoutPadding = heightWithoutPadding
        } else {
            heightWithoutPadding = widthWithoutPadding
        }
        setMeasuredDimension(widthWithoutPadding + paddingLeft + paddingRight,heightWithoutPadding + paddingTop + paddingBottom) // do calculation without padding & then add padding at end
        // bcoz if padding is not equal in all directions then the size calculation will be a problem.

        setMeasuredDimension(measuredWidth-250, measuredHeight-100) // always use measuredWidth , measuredHeight instead of width & height in onMeasure.*/
        println("CustomTextView.onMeasure")
    }

   var p = Paint().apply { color = ContextCompat.getColor(MyApplication.getInstance(), com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.color.colorAccent); strokeWidth = 25f  }
    val greenPaint = Paint().apply { color = ContextCompat.getColor(MyApplication.getInstance(), com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.color.colorPrimary); strokeWidth = 25f }
    var bitmap: Bitmap? = null
    var bitmapCanvas: Canvas? = null
    var i = 0;
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        println("CustomTextView.onDraw called")
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888)
            bitmapCanvas = Canvas(bitmap!!)
            bitmapCanvas!!.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR)
        }

        bitmapCanvas!!.drawCircle(100f , 100f, 100f, paint)
        canvas.drawBitmap(bitmap!!, 50f, 50f, paint)

        val mLeftX = 0f // top.toFloat() dont use left, top... variable in draw ; always treat left = 0, top = 0; right = width ; bottom = height
        val mTopY = 0f

        canvas.drawCircle(0f,0f,100f, paint)
        canvas.drawLine(mLeftX,mTopY, mLeftX+ 125f,mTopY+125f,paint)
        canvas.drawPoints(floatArrayOf(width.toFloat(),height.toFloat()),paint)

        /*if (i > 3 && i <= 6) {
            canvas.drawCircle(mLeftX+50, mTopY+50, 50f, greenPaint)
        } else if ( i > 6){
            canvas.drawCircle(mLeftX+50, mTopY+50, 50f, paint)
        }
        i++*/
    }
}