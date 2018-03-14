package salon.octadevtn.sa.salon.Utils;

/**
 * Created by Marwen octadev on 9/26/2017.
 */

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mutinda Boniface on 7/5/2015.
 */
public class SwipeEvents implements View.OnTouchListener {
    float x1, x2, y1, y2;
    View view;
    private SwipeCallback swipeCallback;
    private SwipeSingleCallback swipeSingleCallback;
    private SwipeDirection detectSwipeDirection;

    private static SwipeEvents newInstance() {
        return new SwipeEvents();
    }

    public static void detect(View view, SwipeCallback swipeCallback) {
        SwipeEvents evt = SwipeEvents.newInstance();
        evt.swipeCallback = swipeCallback;
        evt.view = view;
        evt.detect();
    }

    public static void detectTop(View view, SwipeSingleCallback swipeSingleCallback) {
        SwipeEvents evt = SwipeEvents.newInstance();
        evt.swipeSingleCallback = swipeSingleCallback;
        evt.view = view;
        evt.detectSingle(SwipeDirection.TOP);
    }

    public static void detectRight(View view, SwipeSingleCallback swipeSingleCallback) {
        SwipeEvents evt = SwipeEvents.newInstance();
        evt.swipeSingleCallback = swipeSingleCallback;
        evt.view = view;
        evt.detectSingle(SwipeDirection.RIGHT);
    }

    public static void detectBottom(View view, SwipeSingleCallback swipeSingleCallback) {
        SwipeEvents evt = SwipeEvents.newInstance();
        evt.swipeSingleCallback = swipeSingleCallback;
        evt.view = view;
        evt.detectSingle(SwipeDirection.BOTTOM);
    }

    public static void detectLeft(View view, SwipeSingleCallback swipeSingleCallback) {
        SwipeEvents evt = SwipeEvents.newInstance();
        evt.swipeSingleCallback = swipeSingleCallback;
        evt.view = view;
        evt.detectSingle(SwipeDirection.LEFT);
    }

    private void detect() {
        view.setOnTouchListener(this);
    }

    private void detectSingle(SwipeDirection direction) {
        this.detectSwipeDirection = direction;
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                SwipeDirection direction = null;

                float xDiff = x2 - x1;
                float yDiff = y2 - y1;
                if (Math.abs(xDiff) > Math.abs(yDiff)) {
                    if (x1 < x2) {
                        direction = SwipeDirection.RIGHT;
                    } else {
                        direction = SwipeDirection.LEFT;
                    }
                } else {
                    if (y1 > y2) {
                        direction = SwipeDirection.TOP;
                    } else {
                        direction = SwipeDirection.BOTTOM;
                    }
                }

                // Only trigger the requested event only if there
                if (detectSwipeDirection != null && swipeSingleCallback != null) {
                    if (direction == detectSwipeDirection) {
                        swipeSingleCallback.onSwipe();
                    }
                } else {
                    if (direction == SwipeDirection.RIGHT) {
                        swipeCallback.onSwipeRight();
                    }
                    if (direction == SwipeDirection.LEFT) {
                        swipeCallback.onSwipeLeft();
                    }
                    if (direction == SwipeDirection.TOP) {
                        swipeCallback.onSwipeTop();
                    }
                    if (direction == SwipeDirection.BOTTOM) {
                        swipeCallback.onSwipeBottom();
                    }
                }

                break;
        }
        return false;
    }

    public enum SwipeDirection {
        TOP, RIGHT, BOTTOM, LEFT
    }

    public interface SwipeCallback {
        public void onSwipeTop();

        public void onSwipeRight();

        public void onSwipeBottom();

        public void onSwipeLeft();
    }

    public interface SwipeSingleCallback {
        public void onSwipe();
    }
}
