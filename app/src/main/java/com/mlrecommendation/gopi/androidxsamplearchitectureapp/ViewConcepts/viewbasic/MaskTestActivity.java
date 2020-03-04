package com.mlrecommendation.gopi.androidxsamplearchitectureapp.ViewConcepts.viewbasic;

import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R;
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.CommonUtils;

import java.util.LinkedList;
import java.util.Queue;

public class MaskTestActivity extends AppCompatActivity {

    Bitmap mask, background, filledMask, overlay;
    Canvas c;
    Bitmap myMaskOverlayBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask_bitmap_foreground);
        background = BitmapFactory.decodeResource(getResources(), R.drawable.mask_bitmap_background);

        // get the mask, copy it to filledMask and then flood from the center with CYAN
        filledMask = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        c = new Canvas(filledMask);
        c.drawBitmap(mask, 0, 0, new Paint());
        Point center = new Point(filledMask.getWidth() / 2, filledMask.getHeight() / 2);
        floodFill(filledMask, center, Color.TRANSPARENT, Color.WHITE);


        // create new overlay Bitmap, draw the filledMask and then add the background using PorterDuff
        overlay = Bitmap.createBitmap(filledMask.getWidth(), filledMask.getHeight(), Bitmap.Config.ARGB_8888);
        c = new Canvas(overlay);
        Paint p = new Paint();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        c.drawBitmap(filledMask, 0, 0, new Paint());
        c.drawBitmap(background, 0, 0, p);

        myMaskingTest();

        DrawView drawView = new DrawView(this);
        // set background to light blue in order to see transparent areas
        drawView.setBackgroundColor(0xffd2d7fe);
        setContentView(drawView);
        drawView.requestFocus();
    }

    public class DrawView extends View {
        Paint p = new Paint();
        int top = 0;

        public DrawView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(mask, 0, 0, p);
            top += mask.getHeight();

            canvas.drawBitmap(filledMask, 0, top, p);
            top += filledMask.getHeight();

            canvas.drawBitmap(background, 0, top, p);
            top += background.getHeight();

            canvas.drawBitmap(overlay, 0, top, p);
            top += overlay.getHeight();

//            canvas.drawCircle();

            canvas.drawBitmap(myMaskOverlayBitmap,0,top, p);
        }
    }

    // method from https://stackoverflow.com/a/8925653/852795
    public void floodFill(Bitmap bmp, Point pt, int targetColor, int replacementColor) {

        Queue<Point> q = new LinkedList<>();
        q.add(pt);
        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor) continue;

            Point w = n, e = new Point(n.x + 1, n.y);
            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor)) q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1) && (bmp.getPixel(w.x, w.y + 1) == targetColor)) q.add(new Point(w.x, w.y + 1));
                w.x--;
            }
            while ((e.x < bmp.getWidth() - 1) && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);

                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor)) q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1) && (bmp.getPixel(e.x, e.y + 1) == targetColor)) q.add(new Point(e.x, e.y + 1));
                e.x++;
            }
        }
    }

    public void myMaskingTest(){
        final Bitmap myBackgroundMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mask_bitmap_background);
        Bitmap myForegroundMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mask_bitmap_foreground);

        Bitmap myMaskOverlayBitmap;
        myMaskOverlayBitmap = Bitmap.createBitmap(myForegroundMaskBitmap.getWidth(), myForegroundMaskBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(myMaskOverlayBitmap);


        final Bitmap copiedBitmap = myForegroundMaskBitmap.copy(myForegroundMaskBitmap.getConfig(), true); // make bitmap mutable then only u can fill
        // otherwise it crashes with illegalStateException that bitmap.setPixel() { as its not mutable }
        floodFill(copiedBitmap, new Point(myForegroundMaskBitmap.getWidth()/2, myForegroundMaskBitmap.getHeight()/2),Color.TRANSPARENT, Color.RED);

        final Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(myBackgroundMaskBitmap,0,0, paint);
        canvas.drawBitmap(copiedBitmap,0,0, new Paint());
        this.myMaskOverlayBitmap = myMaskOverlayBitmap;
    }
}