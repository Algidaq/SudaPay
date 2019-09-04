package com.example.sudapay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DrawableUtils;
import android.util.AttributeSet;

public class LogInButton extends AppCompatButton {

    public LogInButton(Context context) {
        super(context);
    }

    public LogInButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LogInButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setAllCaps(false);
//        setLeft(R.drawable.logo2);
        setBackgroundResource(R.drawable.send_button_background);
        setTextColor(Color.WHITE);
        setText("Pay with SudaPay");
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"fonts/cookie.ttf");
        setTypeface(typeface);
        setTextSize(16f);
        Drawable img = getContext().getResources().getDrawable( R.drawable.logo2 );
        img.setBounds( 0, 0, 50, 50 );
        setCompoundDrawables(null , null, img, null );


    }
}
