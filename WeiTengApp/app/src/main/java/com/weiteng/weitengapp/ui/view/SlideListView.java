package com.weiteng.weitengapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

import com.weiteng.weitengapp.R;

/**
 * Created by Admin on 2017/1/15.
 */

public class SlideListView extends ListView {
    public static int MOD_FORBID = 0;
    public static int MOD_LEFT = 1;
    public static int MOD_RIGHT = 2;
    public static int MOD_BOTH = 3;
    private int mode = MOD_FORBID;

    private int leftLength = 0;
    private int rightLength = 0;
    private int slidePosition;
    private int downY;
    private int downX;
    private View itemView;
    private Scroller scroller;
    private int mTouchSlop;
    private boolean canMove = false;
    private boolean isSlided = false;

    public SlideListView(Context context) {
        this(context, null);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideMode);
        mode = a.getInt(R.styleable.SlideMode_mode, 0);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void initSlideMode(int mode) {
        this.mode = mode;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        int lastX = (int) ev.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (this.mode == MOD_FORBID) {
                    return super.onTouchEvent(ev);
                }

                if (isSlided) {
                    scrollBack();
                    return false;
                }

                if (!scroller.isFinished()) {
                    return false;
                }
                downX = (int) ev.getX();
                downY = (int) ev.getY();

                slidePosition = pointToPosition(downX, downY);

                if (slidePosition == AdapterView.INVALID_POSITION) {
                    return super.onTouchEvent(ev);
                }

                itemView = getChildAt(slidePosition - getFirstVisiblePosition());

                if (mode == MOD_BOTH) {
                    leftLength = -itemView.getPaddingLeft();
                    rightLength = -itemView.getPaddingRight();
                } else if (mode == MOD_LEFT) {
                    leftLength = -itemView.getPaddingLeft();
                } else if (mode == MOD_RIGHT) {
                    rightLength = -itemView.getPaddingRight();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (!canMove && slidePosition != AdapterView.INVALID_POSITION && (Math.abs(ev.getX() - downX) > mTouchSlop
                        && Math.abs(ev.getY() - downY) < mTouchSlop)) {
                    int offsetX = downX - lastX;
                    if (offsetX > 0 && (mode == MOD_BOTH || mode == MOD_RIGHT)) {
                        canMove = true;
                    } else canMove = offsetX < 0 && (mode == MOD_BOTH || mode == MOD_LEFT);
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL | (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);
                }
                if (canMove) {
                    requestDisallowInterceptTouchEvent(true);

                    int deltaX = downX - lastX;
                    if (deltaX < 0 && (mode == MOD_BOTH || mode == MOD_LEFT)) {
                        itemView.scrollTo(deltaX, 0);
                    } else if (deltaX > 0 && (mode == MOD_BOTH || mode == MOD_RIGHT)) {
                        itemView.scrollTo(deltaX, 0);
                    } else {
                        itemView.scrollTo(0, 0);
                    }
                    return true;
                }
            case MotionEvent.ACTION_UP:
                if (canMove) {
                    canMove = false;
                    scrollByDistanceX();
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    private void scrollByDistanceX() {
        if (mode == MOD_FORBID) {
            return;
        }
        if (itemView.getScrollX() > 0 && (mode == MOD_BOTH || mode == MOD_RIGHT)) {
            if (itemView.getScrollX() >= rightLength / 2) {
                scrollLeft();
            } else {
                scrollBack();
            }
        } else if (itemView.getScrollX() < 0 && (mode == MOD_BOTH || mode == MOD_LEFT)) {
            if (itemView.getScrollX() <= -leftLength / 2) {
                scrollRight();
            } else {
                scrollBack();
            }
        } else {
            scrollBack();
        }
    }

    private void scrollRight() {
        isSlided = true;
        final int delta = (leftLength + itemView.getScrollX());
        scroller.startScroll(itemView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    private void scrollLeft() {
        isSlided = true;
        final int delta = (rightLength - itemView.getScrollX());
        scroller.startScroll(itemView.getScrollX(), 0, delta, 0, Math.abs(delta));
        postInvalidate();
    }

    private void scrollBack() {
        isSlided = false;
        scroller.startScroll(itemView.getScrollX(), 0, -itemView.getScrollX(), 0, Math.abs(itemView.getScrollX()));
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    public void slideBack() {
        this.scrollBack();
    }
}

