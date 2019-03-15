package com.example.sudapay;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleAngleAnimation extends Animation {
    private MyView circle;

    private float oldAngle;
    private float newAngle;

    private float newX,oldX;
    private float newY,oldY;


    public CircleAngleAnimation(MyView circle, int newAngle) {
        this.oldAngle = circle.getAngle();
        this.newAngle = newAngle;
        this.circle = circle;
    }

    public CircleAngleAnimation(MyView circle, float newX,float newY,int newAngle) {
        this.circle = circle;
        this.newX = newX;
        this.newY=newY;


    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {

        if(newX<100) {
            newX += 5;
            newY += 5;
            circle.setNewX(newX);
            circle.setNewY(newY);
            circle.requestLayout();
            System.out.println("newX"+newX);
        }
        else if (newX<160)
        {
            newX+=5;
            newY-=5;
            circle.setNewX(newX);
            circle.setNewY(newY);
            circle.invalidate();
        }
        else
        {
//            float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);
            this.newAngle = circle.getAngle();
            float angle = newAngle-10;
            circle.setAngle(angle);
            if(angle>=-340)
            circle.invalidate();
        }
    }
}
