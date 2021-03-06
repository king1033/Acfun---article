package acfun.com.article.Swipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import acfun.com.article.R;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;

/**
 *
 */
public class SwipeAppcompatActivity extends AppCompatActivity implements SwipeBackActivityBase {

    private SwipeBackHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackHelper(this);
        mHelper.onActivityCreate();
        overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_close_exit);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    protected void setVibratorEnable(boolean enable){
        mHelper.setVibratorEnabled(enable);
    }

    public void setSwipeListener(SwipeBackLayout.SwipeListener l){
        mHelper.setSwipeListener(l);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}