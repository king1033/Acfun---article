package acfun.com.article;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import acfun.com.article.API.ApiService;
import acfun.com.article.API.UrlApi;
import acfun.com.article.entity.Article;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 *
 */
public class ArticleFragment extends Fragment {

    private Retrofit retrofit;
    private ApiService apiService;

    private WebView webview;
    private WebSettings webSettings;

    private int contentId;

    private View mView;


    public static ArticleFragment newInstance(int contentId){
        Bundle args = new Bundle();
        args.putInt("contentId", contentId);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_article, container, false);
        contentId = getArguments().getInt("contentId");

        webview = (WebView) mView.findViewById(R.id.web_view);

        initRxJava();

        initWebSetting();


        initData();

        return mView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        webview.removeAllViews();
        webview.destroy();
    }

    //初始化WebView设置
    private void initWebSetting(){
        webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //最后加载图片
        webSettings.setBlockNetworkImage(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        /*获得缓存目录 <br>
        * <b>NOTE:</b>请先调用 {@link #isExternalStorageAvailable()} 判断是否可用*/
        String ARTICLE_PATH = ArticleApp.getExternalCacheDir("article").getAbsolutePath();
        //webView设置缓存目录
        webSettings.setAppCachePath(ARTICLE_PATH);
        //优先缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //自动布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //设置Web视图
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {                   //加载网页前

            }

            @Override
            public void onPageFinished(WebView view, String url) {                                  //加载网页后
                webSettings.setBlockNetworkImage(false);

                webview.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\");"
                        + "for(var i=0;i<objs.length;i++)  "
                        + "{"
                        + "    var imgOriSrc =objs[i].getAttribute(\"ori_link\"); "
                        + "    objs[i].setAttribute(\"src\",imgOriSrc);"
                        + "}" + "})()");
            }

        });
    }

    public void initRxJava(){
        retrofit = new Retrofit.Builder()
                .baseUrl(UrlApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

    }

    private void initData(){
        apiService.RxGetArticle(contentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Article, String>() {
                    @Override
                    public String call(Article article) {
                        if (article.isSuccess()) {
                            return changeHtmlDoc(article.getData().getFullArticle().getTxt());
                        }else return null;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("test", "change Html Doc error: " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (s != null) {
                            webview.loadDataWithBaseURL(UrlApi.BASE_URL, s, null, "utf-8", null);
                        }
                    }
                });

    }

    //更改修正html源码适应手机屏幕
    private String changeHtmlDoc(String html) {
        Document doc = Jsoup.parse(html);



        Elements elements = doc.select("p:has(img), div:has(img), span:has(img), li");
        for (Element e : elements) {
            e.removeAttr("style");
            e.removeClass("lazyload");
        }


        elements = doc.select("p[style*=line-height]");
        for (Element e : elements){
            String oStyle = e.attr("style");
            StringBuilder mStyle = new StringBuilder();
            String[] firstTemp = oStyle.split("line-height:");                                           //获取line-height:前的字符
            if (firstTemp.length != 1) {
                String[] lastTemp = firstTemp[1].split(";");
                mStyle.append(firstTemp[0] + "line-height: 1.2;");
                for (int i = 1; i < lastTemp.length; i++) {
                    mStyle.append(lastTemp[i] + ";");
                }
            }else {
                mStyle.append("line-height: 1.2;");
            }
            e.attr("style", mStyle.toString());
        }

        elements = doc.select("[style*=nowrap]");
        for (Element e : elements) {
            StringBuilder mStyle = new StringBuilder();
            String oStyle = e.attr("style");
            String[] firstTemp = oStyle.split("nowrap:");                                           //获取nowrap前的字符
            if (firstTemp.length != 1){
                String[] lastTemp = firstTemp[1].split(";");
                mStyle.append(firstTemp[0] + "nowrap: normal;");
                for (int i = 1; i< lastTemp.length; i++){
                    mStyle.append(lastTemp[i] + ";");
                }
            }else {
                mStyle.append("nowrap: normal;");
            }
            e.attr("style", mStyle.toString());
        }


        elements = doc.select("ul, ol");
        for (Element e : elements){
            e.attr("style", "list-style-type:none; margin:0px; padding:0px;");
        }

        elements = doc.select("div[style*=width]");
        for (Element e : elements) {
            String oStyle = e.attr("style");
            String[] firstTemp = oStyle.split(";width:");                                           //获取width:前的字符
            String[] lastTemp = firstTemp[1].split(";");
            String mStyle = firstTemp[0] + ";max-width: 100%;" + lastTemp[1];
            e.attr("style", mStyle);
        }

        elements = doc.select("img[src]");
        for (Element e : elements) {
            e.removeAttr("alt");
            e.attr("style", "max-width: 100%;max-height: auto; display:block;margin-left:auto;margin-right:auto;");
            String imgUrl = e.attr("src");
            e.attr("src", "file:///android_asset/loading.gif");
            e.attr("ori_link", imgUrl);
        }

        final String fuck = doc.outerHtml();
/*        Log.d("test",fuck);*/
        return fuck;
    }



}
