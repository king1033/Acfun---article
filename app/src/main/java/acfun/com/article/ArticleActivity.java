package acfun.com.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import acfun.com.article.API.ApiService;
import acfun.com.article.API.UrlApi;
import acfun.com.article.Swipe.SwipeAppcompatActivity;
import acfun.com.article.entity.Comments;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArticleActivity extends SwipeAppcompatActivity {

    private int contentId;

    private ArticleFragment articleFragment;
    private CommentFragment commentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private FloatingActionButton fab;

    public static void start(Context context, int contentId){
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("contentId", contentId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        contentId = getIntent().getIntExtra("contentId", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("av" + contentId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        fragmentManager = getSupportFragmentManager();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentFragment = CommentFragment.newInstance(contentId);
                transaction = fragmentManager.beginTransaction();
                transaction.hide(articleFragment);
                transaction.add(R.id.article_fragment_contain, commentFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                fab.hide();
            }
        });

        articleFragment = ArticleFragment.newInstance(contentId);

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
