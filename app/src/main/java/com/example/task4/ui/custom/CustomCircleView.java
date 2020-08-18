package com.example.task4.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.task4.R;

import java.util.Objects;

public class CustomCircleView extends View {

    private static final String TAG = "MyApp";
    private Paint paint;
    private float radius;
    private int color;

    public CustomCircleView(Context context) {
        super(context);
        Log.d(TAG, "CustomCircleView: 1 const");
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        Log.d(TAG, "CustomCircleView: 2 const");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView);
        radius = Float.parseFloat(Objects.requireNonNull(typedArray.getString(R.styleable
                .CustomCircleView_circleRadius)));
        color = typedArray.getColor(R.styleable.CustomCircleView_circleBackground, 0xff000000);
        Log.d(TAG, "CustomCircleView: = " + radius);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        int divideValue = 2;
        float x = (float) getWidth() / divideValue;
        float y = (float) getHeight() / divideValue;
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }
}
