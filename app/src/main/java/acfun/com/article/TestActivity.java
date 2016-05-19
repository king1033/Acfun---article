package acfun.com.article;

import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import acfun.com.article.View.MyFrameLayout;

public class TestActivity extends AppCompatActivity {

    static final String[] typesStr = {"热门", "排行榜", "私信", "我的"};

    private int disy = 0;
    private boolean tabIsShow = true;


    private LinearLayout tabs;
    private MyFrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tabs = (LinearLayout) findViewById(R.id.test_tabs);
        frameLayout = (MyFrameLayout) findViewById(R.id.test_content);

        frameLayout.setScrollListener(new MyFrameLayout.OnScrollListener() {
            @Override
            public void onScrolled(int dx, int dy) {
                if (disy > 20 && tabIsShow){
                    tabIsShow = false;
                    hideTab();
                    disy = 0;
                }
                if (disy < -20 && !tabIsShow){
                    tabIsShow = true;
                    showTab();
                    disy = 0;
                }
                if ((tabIsShow && (dy > 0)) || (!tabIsShow && (dy < 0))){
                    disy += dy;
                }
            }
        });

        TestFragment testFragment = TestFragment.newInstance(110);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.test_content, testFragment);
        transaction.commit();



    }

    private void hideTab(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(tabs, View.TRANSLATION_Y, 0 , tabs.getHeight());
        animator.setDuration(500);
        animator.start();
    }

    private void showTab() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tabs, View.TRANSLATION_Y, tabs.getHeight(), 0);
        animator.setDuration(500);
        animator.start();
    }
}
