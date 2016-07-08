package acfun.com.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.google.gson.JsonObject;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import acfun.com.article.API.ApiService;
import acfun.com.article.entity.Comment;
import acfun.com.article.entity.Comments;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class CommentFragment extends Fragment {

    private View mView;
    private XRecyclerView xRecyclerView;
    private CommentsAdapter adapter;

    private int contentId;

    private Retrofit retrofit;
    private ApiService apiService;
    private ArrayList<Integer> commentIdList = new ArrayList<>();
    private SparseArray<Comment> attr = new SparseArray<>();
    private int page;

    public static CommentFragment newInstance(int contentId) {
        Bundle args = new Bundle();
        args.putInt("contentId", contentId);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_comment, container, false);
        contentId = getArguments().getInt("contentId");
        xRecyclerView = (XRecyclerView) mView.findViewById(R.id.comments_rv);

        adapter = new CommentsAdapter(getContext());
        adapter.getData(commentIdList, attr);
        xRecyclerView.setAdapter(adapter);

        initRxJava();
        initView();

        xRecyclerView.setRefreshing(true);
        return mView;
    }

    public void initRxJava() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.acfun.tv/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private void initView() {
        XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                /*xRecyclerView.reset();*/
                commentIdList.clear();
                attr.clear();
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        };

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        xRecyclerView.setLoadingListener(loadingListener);
    }

    private void loadData(){
        apiService.RxGetComments("comment_list_json.aspx?contentId=" + contentId + "&currentPage=" + page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Comments>() {
                    @Override
                    public void call(Comments comments) {
                        parseContentAttr(comments.getData().getCommentContentArr());
                        commentIdList.addAll(comments.getData().getCommentList());
                    }
                })
                .subscribe(new Observer<Comments>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("test", "error: " + e);
                    }

                    @Override
                    public void onNext(Comments comments) {
                        adapter.notifyDataSetChanged();
                        if (page == 1) {
                            xRecyclerView.refreshComplete();
                        }
                        if (comments.getData().getTotalPage() == page){
                            /*xRecyclerView.noMoreLoading();*/
                        }else {
                            page++;
                            xRecyclerView.loadMoreComplete();
                        }
                    }
                });
    }

    private void parseContentAttr(JsonObject commentContentArr) {

        Gson gson = new Gson();
        for (Map.Entry<String, JsonElement> entry : commentContentArr.entrySet()){
            String key = entry.getKey();
            JsonObject content = commentContentArr.getAsJsonObject(key);
            Comment comment = gson.fromJson(content, Comment.class);
            attr.put(comment.getCid(), comment);
        }
    }

}
