package com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.MyApplication
import kotlinx.android.synthetic.main.activity_view_concepts.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import android.graphics.Shader.TileMode
import android.R.attr.bitmap
import android.graphics.BitmapShader




class ViewConceptsActivity : AppCompatActivity() {
    lateinit var bitmap: Bitmap // reuse exisiting bitmap
    var drawOnMyCanvas: Canvas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.layout.activity_view_concepts)

        customTv.setOnClickListener {
//            customTv.x += 5f
//            customTv.y += 1f
//            customTv.layoutParams = ConstraintLayout.LayoutParams(10,10)
//            customTv.requestLayout()
//            cloneDrawable()
//            customTv.requestLayout()
//            customTv.postInvalidate()

//            drawBitmapOnView()
//            showBitmapView.draw(Canvas(bitmap!!))
//            showBitmapView.requestLayout()
//            resizeBitmapNRender()
//            renderUsingCanvas()
//            setTileMode()
//            drawNestedRoundedRect()
//            drawEmbossingText()
//            drawWithBitmapShader()
            blurMaskFilter()
        }

    }

    fun drawWithBitmapShader(){
        val bitmap1 = BitmapFactory.decodeResource(resources, com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.drawable.computer).copy(Bitmap.Config.ARGB_8888,true)
        val createScaledBitmap = Bitmap.createScaledBitmap(bitmap1, 100, 100, false)
        val shader = BitmapShader(createScaledBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        conceptTv.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        conceptTv.getPaint().setShader(shader);
        conceptTv.requestLayout()
    }

    fun blurMaskFilter(){
//        val filter = BlurMaskFilter(50f, BlurMaskFilter.Blur.NORMAL)
//        conceptTv.setLayerType(View.LAYER_TYPE_SOFTWARE,null)  // I don't know the use of software rendering only here. Its working fine without it as well.
//        conceptTv.getPaint().setMaskFilter(filter);
        val filter = BlendModeColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), BlendMode.DARKEN)
        conceptTv.getPaint().setColorFilter(filter)
        conceptTv.requestLayout()
    }

    fun drawEmbossingText(){
        val filter = EmbossMaskFilter(
            floatArrayOf(1f, 5f, 1f), // direction of the light source
            0.5f, // ambient light between 0 to 1
            30f, // specular highlights
            27.5f // blur before applying lighting
        )
        conceptTv.setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        conceptTv.getPaint().setMaskFilter(filter);
        conceptTv.requestLayout()
    }

    fun drawNestedRoundedRect(){
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawRoundRect(10f,10f,35f,35f, 5f,5f,Paint().apply { isAntiAlias = true;color = ContextCompat.getColor(this@ViewConceptsActivity, com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.color.colorAccent) })
        canvas.drawRect(0f,0f,45f,45f,Paint().apply { isAntiAlias = true;color = ContextCompat.getColor(this@ViewConceptsActivity, com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.color.colorPrimary); style = Paint.Style.STROKE })
        showBitmapView.background = bitmap.toDrawable(resources)
        showBitmapView.requestLayout()
    }

    private fun setTileMode() {
        val bitmap1 = BitmapFactory.decodeResource(resources, com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.drawable.computer).copy(Bitmap.Config.ARGB_8888,true)
        val createScaledBitmap = Bitmap.createScaledBitmap(bitmap1, 100, 100, false)
        val bitmapDrawable = BitmapDrawable(resources, createScaledBitmap)
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        viewConceptParent.background = bitmapDrawable
        viewConceptParent.requestLayout()
    }

    fun cloneDrawable(){ // on every click show clone whats present in customTv & render on showBitmapView
        // You can do for any source view like Parent constraintLayout u can clone it
        if (::bitmap.isInitialized.not()) {
            bitmap = Bitmap.createBitmap(customTv.width, customTv.height, Bitmap.Config.ARGB_8888)
            drawOnMyCanvas = Canvas(bitmap)
        }
        customTv.draw(drawOnMyCanvas) // This makes customTv to draw on this canvas => which is linked to bitmap.

        showBitmapView.background = bitmap.toDrawable(resources)
        showBitmapView.invalidate()
    }

    private fun drawBitmapOnView() {
//        val bitmapOther = ViewUtils.getBitmapFromView(customTv)  // u can get bitmap from existing drawn view
//        val bitmapOther = BitmapFactory.decodeResource(resources, R.drawable.computer) // u can decode from Resource
        BitmapFactory.decodeFile("/gopi/sdcard...")
        BitmapFactory.decodeStream(openFileInput("/gopi/sdcard")) // u can pass a inputStream
        val drawable = ContextCompat.getDrawable(this, com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.drawable.computer)
        val bitmapOther = drawable!!.toBitmap()  // u can get bitmap from drawables
        val bitmap = bitmapOther
//        showBitmapView.background = BitmapDrawable(resources, bitmap) // convert bitmap to BitmapDrawable & render on view
        showBitmapView.background = bitmap.toDrawable(resources) // set bitmap convert to Drawable & render on view using background
        showBitmapView.invalidate() // invalidate to just call onDraw , use requestlayout() for all lifecycle methods.
    }

    private fun resizeBitmapNRender(){

        var bitmap = BitmapFactory.decodeResource(resources,
            com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.drawable.computer, BitmapFactory.Options().apply { inSampleSize = 4 })
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap,10,10,true)
        println(" orginal bitmap size ${bitmap.width} - ${bitmap.height}  ; scaled bitmap size ${scaledBitmap.width} - ${scaledBitmap.height}")
        bitmap = scaledBitmap
        showBitmapView.background = bitmap.toDrawable(resources)
        showBitmapView.invalidate()

//        val bitmap1 = BitmapFactory.decodeFile("/gopi/sdcard", BitmapFactory.Options().apply { inJustDecodeBounds = true })
//        val size:String = bitmap1.width.toString() + " - " + bitmap1.height

        val scaledBitmapOptions = BitmapFactory.Options()
            .apply { inScaled = true; inSampleSize = 2; inDensity = 100; inTargetDensity = (3 / 8) * 100  }
        val bitmap2 = BitmapFactory.decodeResource(resources, com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.drawable.computer, scaledBitmapOptions)
        println("## INDENSITY orginal bitmap size ${originalWidth} - ${originalHeight}; scaled bitmap size ${bitmap2.width} - ${bitmap2.height}")
        // inSampleSize reduces image size by 2, its multiple of 2 like 1/2,1/4,1/8... ; If u want a custom size like 3/8 etc then use nearest 2 power greater than 3/8 in this case its 1/2 => So set inSampleSize = 2 & reset manipulate inTarget/inDesityinDesity = 3/8 give it appropriately.

    }

    fun renderUsingCanvas(){
        val bitmap = Bitmap.createBitmap(showBitmapView.width, showBitmapView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawCircle((showBitmapView.left + 10f),showBitmapView.top+ 10f, 20f, Paint().apply { color = ContextCompat.getColor(MyApplication.getInstance(), com.mlrecommendation.gopi.androidxsamplearchitectureapp.R.color.blue); strokeWidth = 25f }) // make sure that coordinate lie within the view size else it wont render
        showBitmapView.background = bitmap.toDrawable(resources)
        showBitmapView.invalidate()
    }


}
