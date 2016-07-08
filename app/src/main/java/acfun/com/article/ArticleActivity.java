package acfun.com.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;


import acfun.com.article.Swipe.SwipeAppcompatActivity;


public class ArticleActivity extends SwipeAppcompatActivity {

    private String contentId;
    private String title;

    private ArticleFragment articleFragment;
    private CommentFragment commentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private FloatingActionButton fab;

    private Toolbar toolbar;


    public static void start(Context context, String contentId, String title){
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("contentId", contentId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        contentId = getIntent().getStringExtra("contentId");
        title= getIntent().getStringExtra("title");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ac" + contentId);
        toolbar.getBackground().setAlpha(0);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* nestedScrollView = (NestedScrollView) findViewById(R.id.article_fragment_contain);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY <= 100){
                    float alpha = (1f * scrollY / (100));
                    toolbar.getBackground().setAlpha((int)(255 * alpha));
                }else {
                    toolbar.getBackground().setAlpha(255);
                }
            }
        });*/



        fragmentManager = getSupportFragmentManager();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentFragment = CommentFragment.newInstance(Integer.valueOf(contentId));
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.article_fragment_contain, commentFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                fab.hide();
            }
        });

        articleFragment = ArticleFragment.newInstance(Integer.valueOf(contentId));

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.article_fragment_contain, articleFragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            getSwipeBackLayout().scrollToFinishActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!fragmentManager.popBackStackImmediate()) {
            getSwipeBackLayout().scrollToFinishActivity();
        }else {
            fab.show();
        }
    }



}
