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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import java.util.List;

import acfun.com.article.API.ApiService;
import acfun.com.article.API.RetrofitUtil;
import acfun.com.article.API.UrlApi;
import acfun.com.article.entity.FirstImage;
import acfun.com.article.entity.TitlesList;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import retrofit2.Response;
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

    private static final int day = 24 * 60 * 60 * 1000;
    private static final int week = 7 * 24 * 60 * 60 * 1000;
    private int range = day;
    private int type = 110;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;
    private XRecyclerView mRecyclerView;
    public RvAdapter adapter ;
    private View mView;
    private Handler handler;


    private List<FirstImage> networkImages;


    private RelativeLayout relativeLayout;
    private ConvenientBanner convenientBanner;
    private CBViewHolderCreator viewHolderCreator;
    private Spinner spinnerType;
    private Spinner spinnerRange;


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
            relativeLayout = (RelativeLayout) inflater.inflate(R.layout.main_fragment_header, null);


            adapter = new RvAdapter(inflater, getContext(), 2);
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
        relativeLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 480));
        convenientBanner = (ConvenientBanner) relativeLayout.findViewById(R.id.convenientBanner);
        spinnerType = (Spinner) relativeLayout.findViewById(R.id.spinner_type);
        spinnerRange = (Spinner) relativeLayout.findViewById(R.id.spinner_range);

        initSwipeRefreshLayout();
        initRecyclerView();
        initSpin();
    }

    private void initSwipeRefreshLayout(){
        //RefreshLayout刷新监听器
        onRefreshListener =
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
                        FirstImage temp = networkImages.get(position);
                        ArticleActivity.start(getContext(), temp.getContentId(), temp.getImgText());
                    }
                });
    }

    private void initSpin(){
        spinnerRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switchRange(pos);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switchType(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                        mRecyclerView.addHeaderView(relativeLayout);
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
                .client(RetrofitUtil.genericClient())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void loadData(){
        apiService.GetTitleList(1, 0, 20, type, range)
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
                        Log.d("test", "mainFragment: " + e.toString());
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
                        if (titlesList.getCode() != 200){
                            Log.d("test", "mainFragment:" + titlesList.getMessage());
                        }
                        adapter.getTitles(titlesList.getData().getList());
                    }
                });
    }

    private void switchRange(int pos){
        switch (pos){
            case 0:
                if (range != day){
                    range = day;
                    refreshData();
                }
                break;
            case 1:
                if (range != week){
                    range = week;
                    refreshData();
                }
                break;
            default:
                break;
        }
    }

    private void switchType(int pos) {
        switch (pos){
            case 0:
                setType(110);
                break;
            case 1:
                setType(73);
                break;
            case 2:
                setType(74);
                break;
            case 3:
                setType(75);
                break;
            case 4:
                setType(164);
                break;
        }
    }

    private void setType(int type){
        if (this.type != type){
            this.type = type;
            refreshData();
        }
    }

    private void refreshData(){
        swipeRefreshLayout.setRefreshing(true);
        onRefreshListener.onRefresh();
    }
}



