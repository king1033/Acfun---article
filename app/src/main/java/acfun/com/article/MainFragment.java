package acfun.com.article;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import acfun.com.article.API.TitleApi;
import acfun.com.article.entity.ArticleTitle;
import acfun.com.article.entity.Pages;
import acfun.com.article.util.GetAndParseUrl;

/**
 *
 */
public class MainFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;


    private View mView;
    private Handler handler;

    private Boolean isLoading = false;
    private int pageCount = 1;

    private RecyclerView mRecyclerView;


    private List<ArticleTitle> data = new ArrayList<>();
    private RvAdapter adapter ;

    private MainActivity mainActivity;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        handler = new Handler();

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.rv_swipe);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recycler_view);

        mainActivity = (MainActivity)getActivity();

        initView();
        initData();

        return mView;
    }


    private void initView(){

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new RvAdapter(getActivity(), data);
        mRecyclerView.setAdapter(adapter);

        mainActivity.hideFab();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                }, 2000);
            }
        });

        mRecyclerView.addOnScrollListener(new MyRecyclerViewScrollLSN() {
            @Override
            void onScrollUp() {
                mainActivity.hideFab();
            }

            @Override
            void onScrollDown() {
                mainActivity.showFab();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mainActivity.showFab();
                            }
                        });
                        getData(++pageCount);
                        Log.d("test", "load more completed");
                        isLoading = false;
                    }
                }
            }

        });

        //添加点击事件
        adapter.setOnItemClickListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("test", "item position = " + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d("test", "item long position = " + position);
            }
        });
    }


    public void initData(){
        GetAndParseUrl getAndParseUrl = new GetAndParseUrl(TitleApi.getBaseUrl(0,0,10,0));
        getAndParseUrl.sendHttpRequest(new GetAndParseUrl.PagesCallbackListener() {
            @Override
            public void onFinish(Pages pages) {
                pageCount = 1;
                data.clear();
                data.addAll(pages.getArticleTitles());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Exception e) {

                Log.d("test", e.toString());
            }
        });
    }


    private void getData(int i){
        GetAndParseUrl getAndParseUrl = new GetAndParseUrl(TitleApi.getBaseUrl(0,0,10,i));
        getAndParseUrl.sendHttpRequest(new GetAndParseUrl.PagesCallbackListener() {
            @Override
            public void onFinish(Pages pages) {
                data.addAll(pages.getArticleTitles());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Exception e) {

                Log.d("test", e.toString());
            }
        });

    }

}

abstract class MyRecyclerViewScrollLSN extends RecyclerView.OnScrollListener {
    private int mScrollThreshold;

    abstract void onScrollUp();

    abstract void onScrollDown();

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("test", "StateChanged = " + newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //判断向上向下滑
        setScrollThreshold(4);
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (dy > 0) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
    }
    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }
}
