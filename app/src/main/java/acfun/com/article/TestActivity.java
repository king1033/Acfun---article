package acfun.com.article;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import acfun.com.article.Swipe.SwipeAppcompatActivity;

public class TestActivity extends SwipeAppcompatActivity {

    static final String[] typesStr = {"文章",  "评论"};

    private String contentId;
    private String title;

    private ImageButton btBack;

    private TabLayout tabs;
    private ViewPager viewPager;
    private Handler handler;
    private PageAdapter adapter;
    private List<Fragment> fragments;

    private ArticleFragment articleFragment;
    private CommentFragment commentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    public static void start(Context context, String contentId, String title){
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra("contentId", contentId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        contentId = getIntent().getStringExtra("contentId");
        title= getIntent().getStringExtra("title");

        btBack = (ImageButton) findViewById(R.id.button_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSwipeBackLayout().scrollToFinishActivity();
            }
        });

        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        handler = new Handler();
        fragments = new ArrayList<>();
        for (String a : typesStr){
            tabs.addTab(tabs.newTab().setText(a));
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                articleFragment = ArticleFragment.newInstance(Integer.valueOf(contentId));
                commentFragment = CommentFragment.newInstance(Integer.valueOf(contentId));
                fragments.add(articleFragment);
                fragments.add(commentFragment);
                adapter = new PageAdapter(getSupportFragmentManager(), typesStr, fragments);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setAdapter(adapter);
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                if (position == 0) {
                                    setSwipeBackEnable(true);
                                }else {
                                    setSwipeBackEnable(false);
                                }
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                        tabs.setupWithViewPager(viewPager);

                    }
                });
            }
        }).start();
        fragmentManager = getSupportFragmentManager();

    }


    @Override
    public void onBackPressed() {
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
