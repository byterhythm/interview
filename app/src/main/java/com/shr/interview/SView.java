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


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#ff00ff"));
        canvas.drawRect(new Rect(0, 0, 100, 100), paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dex = (int) (mLastX - x);
                int dey = (int) (mLastY - y);
                Log.i("shr", "dex=" + dex + ";dey=" + dey);
                smartScrollTo(dex, dey);
                break;
            case MotionEvent.ACTION_UP:
                mLastX = x;
                mLastY = y;
                break;
        }
        return true;
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
