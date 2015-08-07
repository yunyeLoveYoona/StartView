package com.example.administrator.startview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.startview.R;

/**
 * Created by yunye on 15-8-7.
 */
public class StartView extends ImageView {
    private static final Xfermode MASK_XFERMODE;
    private int color;
    private int colorMask;
    private int progress = 45;

    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_ATOP;
        MASK_XFERMODE = new PorterDuffXfermode(localMode);
    }

    private Bitmap mask;
    private Paint paint;

    public StartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        color = context.getResources().getColor(R.color.yellow);
        colorMask = context.getResources().getColor(R.color.white);
        paint = new Paint();
        paint.setColor(color);
        paint.setFilterBitmap(false);
        paint.setXfermode(MASK_XFERMODE);
    }

    private float degree2Radian(float degree) {
        return (float) (Math.PI * degree / 180);
    }

    public Bitmap createStart() {
        Bitmap localBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setStrokeWidth(3);
        localPaint.setColor(colorMask);
        Path path = new Path();
        float radian = degree2Radian(36);
        float radius = getWidth() / 2;
        float radius_in = (float) (radius * Math.sin(radian / 2) / Math
                .cos(radian));
        path.moveTo((float) (radius * Math.cos(radian / 2)), 0);
        path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in
                        * Math.sin(radian)),
                (float) (radius - radius * Math.sin(radian / 2)));
        path.lineTo((float) (radius * Math.cos(radian / 2) * 2),
                (float) (radius - radius * Math.sin(radian / 2)));
        path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in
                        * Math.cos(radian / 2)),
                (float) (radius + radius_in * Math.sin(radian / 2)));
        path.lineTo(
                (float) (radius * Math.cos(radian / 2) + radius
                        * Math.sin(radian)), (float) (radius + radius
                        * Math.cos(radian)));
        path.lineTo((float) (radius * Math.cos(radian / 2)),
                (float) (radius + radius_in));
        path.lineTo(
                (float) (radius * Math.cos(radian / 2) - radius
                        * Math.sin(radian)), (float) (radius + radius
                        * Math.cos(radian)));
        path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in
                        * Math.cos(radian / 2)),
                (float) (radius + radius_in * Math.sin(radian / 2)));
        path.lineTo(0, (float) (radius - radius * Math.sin(radian / 2)));
        path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in
                        * Math.sin(radian)),
                (float) (radius - radius * Math.sin(radian / 2)));
        path.close();
        localCanvas.drawPath(path, localPaint);
        return localBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            Bitmap background = Bitmap.createBitmap(getWidth(), getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas backgroundCanvs = new Canvas(background);
            Paint tempPaint = new Paint();
            tempPaint.setColor(color);
            tempPaint.setStyle(Paint.Style.FILL);
            backgroundCanvs.drawRect(0, 0, progress * getWidth() / 100, getHeight(), tempPaint);
            int count = canvas.saveLayer(0.0F, 0.0F, getWidth(),
                    getHeight(), null, 31);
            canvas.drawBitmap(background, 0, 0, null);
            if ((this.mask == null) || (this.mask.isRecycled())) {
                this.mask = createStart();
            }
            canvas.drawBitmap(this.mask, 0.0F, 0.0F, this.paint);
            canvas.restoreToCount(count);
        } catch (Exception localException) {

        }
    }

    public void setStartColor(int color) {
        this.color = color;
    }

    public void setProgress() {
        this.progress = progress;
    }
}
