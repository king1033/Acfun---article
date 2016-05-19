package acfun.com.article.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;


/**
 *
 */
public class MyFrameLayout extends FrameLayout {

    private int lastY;
    private int lastX;
    private OnScrollListener listener;

    public MyFrameLayout(Context context){
        this(context, null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setScrollListener(OnScrollListener listener){
        this.listener = listener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                lastX = rawX;
                lastY = rawY;
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                lastX = rawX;
                lastY = rawY;
                listener.onScrolled(-offsetX, -offsetY);
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    public interface OnScrollListener{
        public void onScrolled(int dx, int dy);
    }
}
