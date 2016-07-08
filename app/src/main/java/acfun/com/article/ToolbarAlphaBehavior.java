package acfun.com.article;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.a.a.V;

/**
 *
 */
public class ToolbarAlphaBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private static final String TAG = "ToolbarAlphaBehavior";
    private int mScollHeight = 0;
    private Context context;

    public ToolbarAlphaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, Toolbar toolbar,
                                       View directTargetChild, View target, int nestedScrollAxes) {
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar toolbar, View target,
                                  int dx, int dy, int[] consumed) {
        /*mScollHeight = target.getScrollY();



        if(mScollHeight < 300){
            float alpha = (1f * mScollHeight / (300));
            toolbar.getBackground().setAlpha((int)(255 * alpha));
        }*/
    }


}
