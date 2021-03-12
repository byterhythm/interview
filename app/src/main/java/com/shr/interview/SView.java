package com.shr.interview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import androidx.annotation.Nullable;

public class SView extends View {

    public SView(Context context) {
        super(context);
    }

    public SView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    private float mLastX, mLastY;
    Scroller scroller;

    private int mWidth = 100;
    private int mHeight = 100;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (MeasureSpec.AT_MOST == widthMode && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (MeasureSpec.AT_MOST == widthMode) {
            setMeasuredDimension(mWidth, MeasureSpec.getSize(heightMeasureSpec));
        } else if (MeasureSpec.AT_MOST == heightMode) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mHeight);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#ff00ff"));
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
    }

    public void smartScrollTo(int dx, int dy) {
        int scrX = getScrollX();
        int scrY = getScrollY();
        int pianyiX = dx - scrX;
        int pianyiY = dx - scrX;
        scroller.startScroll(scrX, scrY, pianyiX, pianyiY, 100);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }

    }
}
