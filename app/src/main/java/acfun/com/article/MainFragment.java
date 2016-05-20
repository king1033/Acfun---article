package acfun.com.article;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import java.util.List;

import acfun.com.article.API.ApiService;
import acfun.com.article.API.UrlApi;
import acfun.com.article.entity.FirstImage;
import acfun.com.article.entity.TitlesList;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
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
    private Handler handler;


    private List<FirstImage> networkImages;


    private ConvenientBanner convenientBanner;
    private CBViewHolderCreator viewHolderCreator;


    private Retrofit retrofit;
    private ApiService apiService;



    public static MainFragment newInstance(int channelId){
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("channelId", channelId);
        mainFragment.setArguments(args);
        return mainFragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_main, container, false);
            convenientBanner = (ConvenientBanner) inflater.inflate(R.layout.main_fragment_header, null);
            adapter = new RvAdapter(inflater, getContext());
            handler = new Handler();

            initView();
            initRxJava();

            initData();

        }
        return mView;
    }

    // 开始自动翻页
    @Override
    public void onResume(){
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    private void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.rv_swipe);
        mRecyclerView = (XRecyclerView) mView.findViewById(R.id.id_recycler_view);
        convenientBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));

        swipeRefreshLayoutInit();
        initRecyclerView();
    }

    private void swipeRefreshLayoutInit(){
        //RefreshLayout刷新监听器
        SwipeRefreshLayout.OnRefreshListener onRefreshListener =
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        adapter.clearTitles();
                        adapter.notifyDataSetChanged();
                        loadData();
                    }
                };
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
    }

    private void initRecyclerView(){
        mRecyclerView.setAdapter(adapter);
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
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    private void initBanner(){
        viewHolderCreator = new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        };

        convenientBanner.setPages(viewHolderCreator, networkImages)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ArticleActivity.start(getContext(), Integer.valueOf(networkImages.get(position).getContentId()));
                    }
                });
    }


    public void initData(){
        BmobQuery<FirstImage> query = new BmobQuery<>();
        query.findObjects(getContext(), new FindListener<FirstImage>() {
            @Override
            public void onSuccess(List<FirstImage> list) {
                networkImages = list;
                initBanner();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                        mRecyclerView.addHeaderView(convenientBanner);
                        loadData();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                Log.d("test", "Error: " + s);
            }
        });

    }

    public void initRxJava(){
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlApi.OFFICIAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void loadData(){
        apiService.GetTitleList(2, 1, 0, 20, 0, 110, 24 * 60 * 60 * 1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TitlesList>() {
                    @Override
                    public void onCompleted() {
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
                        adapter.getTitles(titlesList.getData().getList());
                    }
                });
    }
}



