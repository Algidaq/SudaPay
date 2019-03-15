package com.example.sudapay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

    private static final int START_ANGLE_POINT = -37;

    private final Paint paint;
    private final RectF rect;
    private float angle;
    private float oldX=0f,newX=70f;
    private float oldY=0f,newY=70f;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 5;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(getContext().getResources().getColor(R.color.moneyInColor));
        paint.setDither(true);


        //size 200x200 example
        rect = new RectF(15, strokeWidth, 180 , 160 );

        //Initial Angle (optional, it can be zero)
        angle = -37;

    }

    int callOnce=-1;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float [] pts = {70,70,newX,newY};
        if(newX<=100)
        {
            canvas.drawLines(pts,paint);

        }
        else
        {
            if(newX<160) {

                float[] pts2 = {70, 70, 100, 100, 98, 98, newX, newY};
                canvas.drawLines(pts2, paint);
            }
            else
            {
                float[] pts2 = {70, 70, 100, 100, 98, 98, newX+2, newY-6};
                canvas.drawLines(pts2, paint);
                canvas.drawArc(rect,START_ANGLE_POINT,angle,false,paint);
            }

        }


    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getNewX() {
        return newX;
    }

    public float getNewY() {
        return newY;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }
}
