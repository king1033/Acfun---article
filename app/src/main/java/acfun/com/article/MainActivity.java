package acfun.com.article;

import android.animation.ObjectAnimator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import acfun.com.article.View.MyFrameLayout;

public class MainActivity extends AppCompatActivity {


    private int disy = 0;
    private boolean tabIsShow = true;

    private FragmentManager fragmentManager;

    private LinearLayout tabs;
    private MyFrameLayout frameLayout;
    private MainFragment testFragment;
    private ImageButton button1;
    private RankFragment rankFragment;
    private ImageButton button2;
    private LetterFragment letterFragment;
    private ImageButton button3;
    private UserFragment userFragment;
    private ImageButton button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (LinearLayout) findViewById(R.id.test_tabs);
        frameLayout = (MyFrameLayout) findViewById(R.id.test_content);
        button1 = (ImageButton) findViewById(R.id.button1);
        button2 = (ImageButton) findViewById(R.id.button2);
        button3 = (ImageButton) findViewById(R.id.button3);
        button4 = (ImageButton) findViewById(R.id.button4);

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


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button1:
                        setTabSelection(1);
                        break;
                    case R.id.button2:
                        setTabSelection(2);
                        break;
                    case R.id.button3:
                        setTabSelection(3);
                        break;
                    case R.id.button4:
                        setTabSelection(4);
                        break;
                    default:
                        break;
                }
            }
        };
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);

        fragmentManager = getSupportFragmentManager();
        setTabSelection(1);

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

    private void setTabSelection(int index){

        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 1:
                button1.setImageResource(R.drawable.ic_home_blue_24dp);
                if (testFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    testFragment = MainFragment.newInstance(110);
                    transaction.add(R.id.test_content, testFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(testFragment);
                }
                break;
            case 2:
                button2.setImageResource(R.drawable.ic_equalizer_blue_24dp);
                if (rankFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    rankFragment = new RankFragment();
                    transaction.add(R.id.test_content, rankFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(rankFragment);
                }
                break;
            case 3:
                button3.setImageResource(R.drawable.ic_message_blue_24dp);
                if (letterFragment == null){
                    letterFragment = new LetterFragment();
                    transaction.add(R.id.test_content, letterFragment);
                }
                else {
                    transaction.show(letterFragment);
                }
                break;
            case 4:
                button4.setImageResource(R.drawable.ic_more_horiz_blue_24dp);
                if (userFragment == null){
                    userFragment = new UserFragment();
                    transaction.add(R.id.test_content, userFragment);
                }else {
                    transaction.show(userFragment);
                }
            default:
                break;
        }
        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (testFragment != null) {
            button1.setImageResource(R.drawable.ic_home_black_24dp);
            transaction.hide(testFragment);
        }
        if (rankFragment != null) {
            button2.setImageResource(R.drawable.ic_equalizer_black_24dp);
            transaction.hide(rankFragment);
        }
        if (letterFragment != null) {
            button3.setImageResource(R.drawable.ic_message_black_24dp);
            transaction.hide(letterFragment);
        }
        if (userFragment != null) {
            button4.setImageResource(R.drawable.ic_more_horiz_black_24dp);
            transaction.hide(userFragment);
        }
    }
}
