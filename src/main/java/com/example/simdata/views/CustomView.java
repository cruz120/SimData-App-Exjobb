package com.example.simdata.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class CustomView extends View {

    private  Rect mRectSquare;
    private Paint mPaintSquare;

    public CustomView(Context context) {
        super(context);

        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
          mRectSquare = new Rect();
          mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas){

      /*  int[] colors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
        int padding = 10;
        float rectangleWidth = (getMeasuredWidth() - padding * 2) / colors.length;
        for (int i = 0; i < colors.length; i++) {
            mPaintSquare.setColor(colors[i]);
            canvas.drawRect(padding + (rectangleWidth * i), getMeasuredHeight() / 2,
                    padding + rectangleWidth * (i + 1), getMeasuredHeight() - padding, mPaintSquare);
 // 5 px is the padding given to the canvas
        } */

        super.onDraw(canvas);

        mRectSquare.left = 0;
        mRectSquare.top = 0;
        mRectSquare.right = 250;
        mRectSquare.bottom = 125;
        mPaintSquare.setStyle(Paint.Style.FILL);
        mPaintSquare.setColor(Color.CYAN);
        canvas.drawRect(mRectSquare,mPaintSquare);

        mPaintSquare.setStyle(Paint.Style.STROKE);
        mPaintSquare.setColor(Color.BLACK);
        canvas.drawRect(mRectSquare, mPaintSquare);


        mRectSquare.left = 250;
        mRectSquare.top = 0;
        mRectSquare.right = 500;
        mRectSquare.bottom = 250;
        mPaintSquare.setStyle(Paint.Style.FILL);
        mPaintSquare.setColor(Color.CYAN);
        canvas.drawRect(mRectSquare,mPaintSquare);

        mPaintSquare.setStyle(Paint.Style.STROKE);
        mPaintSquare.setColor(Color.BLACK);
        canvas.drawRect(mRectSquare, mPaintSquare);


        mRectSquare.left = 0;
        mRectSquare.top = 125;
        mRectSquare.right = 500;
        mRectSquare.bottom = 250;
        mPaintSquare.setStyle(Paint.Style.FILL);
        mPaintSquare.setColor(Color.CYAN);
        canvas.drawRect(mRectSquare,mPaintSquare);

        mPaintSquare.setStyle(Paint.Style.STROKE);
        mPaintSquare.setColor(Color.BLACK);
        canvas.drawRect(mRectSquare, mPaintSquare);


        mRectSquare.left = 0;
        mRectSquare.top = 250;
        mRectSquare.right = 500;
        mRectSquare.bottom = 500;
        mPaintSquare.setStyle(Paint.Style.FILL);
        mPaintSquare.setColor(Color.CYAN);
        canvas.drawRect(mRectSquare,mPaintSquare);

        mPaintSquare.setStyle(Paint.Style.STROKE);
        mPaintSquare.setColor(Color.BLACK);
        canvas.drawRect(mRectSquare, mPaintSquare);


    }
}
/*
    int max = 500;
    int half = 250;
    int quart = 125;
    int low = 0;


            mRectSquare.left = low;
                    mRectSquare.top = low;
                    mRectSquare.right = half;
                    mRectSquare.bottom = quart;
                    mPaintSquare.setStyle(Paint.Style.FILL);
                    mPaintSquare.setColor(Color.CYAN);
                    canvas.drawRect(mRectSquare, mPaintSquare);

                    mPaintSquare.setStyle(Paint.Style.STROKE);
                    mPaintSquare.setColor(Color.BLACK);
                    canvas.drawRect(mRectSquare, mPaintSquare);


                    mRectSquare.left = half;
                    mRectSquare.top = low;
                    mRectSquare.right = max;
                    mRectSquare.bottom = quart;
                    mPaintSquare.setStyle(Paint.Style.FILL);
                    mPaintSquare.setColor(Color.CYAN);
                    canvas.drawRect(mRectSquare, mPaintSquare);

                    mPaintSquare.setStyle(Paint.Style.STROKE);
                    mPaintSquare.setColor(Color.BLACK);
                    canvas.drawRect(mRectSquare, mPaintSquare);


                    mRectSquare.left = low;
                    mRectSquare.top = quart;
                    mRectSquare.right = max;
                    mRectSquare.bottom = half;
                    mPaintSquare.setStyle(Paint.Style.FILL);
                    mPaintSquare.setColor(Color.CYAN);
                    canvas.drawRect(mRectSquare, mPaintSquare);

                    mPaintSquare.setStyle(Paint.Style.STROKE);
                    mPaintSquare.setColor(Color.BLACK);
                    canvas.drawRect(mRectSquare, mPaintSquare);


                    mRectSquare.left = low;
                    mRectSquare.top = half;
                    mRectSquare.right = max;
                    mRectSquare.bottom = max;
                    mPaintSquare.setStyle(Paint.Style.FILL);
                    mPaintSquare.setColor(Color.CYAN);
                    canvas.drawRect(mRectSquare, mPaintSquare);

                    mPaintSquare.setStyle(Paint.Style.STROKE);
                    mPaintSquare.setColor(Color.BLACK);
                    canvas.drawRect(mRectSquare, mPaintSquare);

                    */