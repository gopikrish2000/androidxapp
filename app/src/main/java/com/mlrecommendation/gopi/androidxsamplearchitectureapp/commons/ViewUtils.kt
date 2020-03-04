package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 * Created by Gopi Krishna on 2020-01-24.
 */
object ViewUtils {

    fun getBitmapFromView(view: View): Bitmap? {
        try {
            view.isDrawingCacheEnabled = false
            view.isDrawingCacheEnabled = true
            var drawingCache: Bitmap? = view.drawingCache // get bitmap directly from view drawing Cache
            if (drawingCache == null) {  // Manually draw bitmap ,happens in some devices like xiomi
                if (view.layoutParams.width > 0 && view.layoutParams.height > 0) {
                    val bitmap =
                        Bitmap.createBitmap(view.layoutParams.width, view.layoutParams.height, Bitmap.Config.ARGB_8888)
                    val c = Canvas(bitmap)
                    view.layout(0, 0, view.layoutParams.width, view.layoutParams.height) // assigns size & position for the view & its childs;; positions r relative to parent ( left, top, right, bottom) . I think this should work without the need of this method call.
                    view.draw(c)  // makes the view to draw on this canvas which is associated to bitmap.
                    drawingCache = bitmap
                }
            }
            return if (drawingCache == null) null else Bitmap.createBitmap(drawingCache)
        } catch (e: OutOfMemoryError) {  // Creating Bitmaps is memory intensive operation , adding this as safe check for CAM-1598
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}