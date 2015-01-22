package pl.tajchert.spritzerwear;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class WearableListOnlyVertical extends WearableListView {

    public WearableListOnlyVertical(Context context) {
        super(context);
    }

    public WearableListOnlyVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WearableListOnlyVertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }
}
