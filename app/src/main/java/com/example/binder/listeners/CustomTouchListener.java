package com.example.binder.listeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;

public class CustomTouchListener implements View.OnTouchListener {
    GestureDetectorCompat gestureDetectorCompat;
    Context context;

    private static String TAG = "CustomTouchListener";

    public CustomTouchListener(Context context) {
        this.context = context;
        gestureDetectorCompat = new GestureDetectorCompat(context,
                new CustomGestureListener());
    }

    public class CustomGestureListener
            extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_DIST_THRESHOLD = 10;
        private static final int SWIPE_VEL_THRESHOLD = 10;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            Log.d(TAG, "swiped");
            float distX = e2.getX() - e1.getX();
            float distY = e2.getY() - e1.getY();
            if (Math.abs(distX) > Math.abs(distY) &&
                    Math.abs(distX) > SWIPE_DIST_THRESHOLD &&
                    Math.abs(velocityX) > SWIPE_VEL_THRESHOLD) {
                if (distX > 0) { //horizontal swipe has occured
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
            } else if (Math.abs(distY) > Math.abs(distX) &&
                    Math.abs(distY) > SWIPE_DIST_THRESHOLD &&
                    Math.abs(velocityY) > SWIPE_VEL_THRESHOLD) {
                if (distY > 0) {
                    onSwipeDown();
                } else {
                    onSwipeUp();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }



    }

    public void onSwipeUp() {
    }

    public void onSwipeDown() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetectorCompat.onTouchEvent(motionEvent);
    }
}
