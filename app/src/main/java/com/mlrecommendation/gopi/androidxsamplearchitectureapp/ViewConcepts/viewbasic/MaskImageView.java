package com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic;


import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

/** Note - this simple implementation only works with Bitmaps. */
public class MaskImageView extends AppCompatImageView {
    // A shader that we will use to draw the masked image.
    private BitmapShader shader;
    // The paint to which we'll apply the shader.
    private Paint paint;
    // The alpha-only Bitmap to be used as the mask.
    private Bitmap mask;
    
    public MaskImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // create the BitmapShader, pointing it to the bitmap of the image this ImageView started with
        shader = new BitmapShader(((BitmapDrawable)getDrawable()).getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // create our paint and attach the shader to it
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        paint.setStyle(Paint.Style.FILL);
    }
    
    @Override
    public void setImageBitmap(Bitmap bm){
        super.setImageBitmap(bm);

        // update our shader
        shader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    /** Provide a Bitmap to mask this view's image. Only the alpha component of the mask will be considered. */
    public void setImageMask(Bitmap maskBitmap){
        this.mask = convertToAlphaMask(maskBitmap);
    }
    
    /** 
     * BitmapShader works with {@link Canvas#drawBitmap(Bitmap, float, float, Paint)} only if the drawn bitmap
     * is purely alpha; to guarantee this, we convert the provided mask ourselves.
     */
    private static Bitmap convertToAlphaMask(Bitmap mask){
        Bitmap alphaMask = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas(alphaMask);
        canvas.drawBitmap(mask, 0.0f, 0.0f, null);
        return alphaMask;
    }

    @Override
    public void onDraw(Canvas canvas){
        // draw our mask, using our image as the shader
        if(null != mask) canvas.drawBitmap(mask, 0, 0, paint);
        else super.onDraw(canvas);
    }
}