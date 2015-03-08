package pl.tajchert.spritzerwear;

import android.content.Context;
import android.support.wearable.view.GridViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class GridViewPagerOnlyHorizontal extends GridViewPager {
    private int mDiffX;
    private int mDiffY;
    private float mLastX;
    private float mLastY;

    public GridViewPagerOnlyHorizontal(Context context) {
        super(context);
    }

    public GridViewPagerOnlyHorizontal(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewPagerOnlyHorizontal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // reset difference values
                mDiffX = 0;
                mDiffY = 0;

                mLastX = ev.getX();
                mLastY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                mDiffX += Math.abs(curX - mLastX);
                mDiffY += Math.abs(curY - mLastY);
                mLastX = curX;
                mLastY = curY;

                if (mDiffX > mDiffY) {
                     // horizontal touch (do nothing
                } else {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
