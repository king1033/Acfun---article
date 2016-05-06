package acfun.com.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import acfun.com.article.API.ApiService;
import acfun.com.article.API.UrlApi;
import acfun.com.article.entity.TitlesList;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class MainFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private XRecyclerView mRecyclerView;
    public RvAdapter adapter ;
    private View mView;

    private Retrofit retrofit;
    private ApiService apiService;


    private int channelId;
    private int page = 1;


    public static MainFragment newInstance(int channelId){
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("channelId", channelId);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    //RefreshLayout刷新监听器
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            adapter.clearTitles();
            adapter.notifyDataSetChanged();
            loadData();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_main, container, false);
            channelId = getArguments().getInt("channelId");

            swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.rv_swipe);
            mRecyclerView = (XRecyclerView) mView.findViewById(R.id.id_recycler_view);


            adapter = new RvAdapter(inflater, getContext());
            mRecyclerView.setAdapter(adapter);

            initRxJava();
            initView();

        }

        return mView;
    }

    @Override
    public void onViewCreated(View container, @Nullable Bundle savedInstanceState){

    }

    private void initView(){

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        //滑动监听器
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        //必须这样设置才能实现一打开界面自动刷新
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        //监听器也要刷新
        onRefreshListener.onRefresh();


    }

    public void initRxJava(){
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

    }

    public void loadData(){
        apiService.RxGetTitlesList("apiserver/content/channel?orderBy=0&channelId=" + channelId +"&pageSize=10&pageNo=" + page)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TitlesList>() {
                    @Override
                    public void onCompleted() {
                        page++;
                        mRecyclerView.loadMoreComplete();
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("test", e.toString());
                        Toast.makeText(mView.getContext(),"刷新失败", Toast.LENGTH_SHORT).show();
                        mRecyclerView.loadMoreComplete();
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                    @Override
                    public void onNext(TitlesList titlesList) {
                        adapter.getTitles(titlesList.getData().getPage().getList());
                    }
                });
    }









}



