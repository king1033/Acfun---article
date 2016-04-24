package acfun.com.article;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import acfun.com.article.entity.Article;
import acfun.com.article.entity.Pages;
import acfun.com.article.util.GetAndParseHTML;
import acfun.com.article.util.GetAndParseUrl;

/**
 *
 */
public class ArticleFragment extends Fragment {

    private WebView webview;
    private View mView;
    private int contentId;
    private String htmlData;
    private MainActivity mainActivity;
    private Handler handler;

    public static ArticleFragment newInstance(int contentId){
        Bundle args = new Bundle();
        args.putInt("contentId", contentId);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ArticleFragment newInstance(String htmlData){
        Bundle args = new Bundle();
        args.putString("htmlData", htmlData);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_article, container, false);
        handler = new Handler();
        contentId = getArguments().getInt("contentId");

        mainActivity = (MainActivity) getActivity();
        mainActivity.changeFab();

        webview = (WebView) mView.findViewById(R.id.web_view);
        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);



        GetAndParseUrl getAndParseUrl = new GetAndParseUrl("http://api.acfun.tv/apiserver/content/article?contentId=" + contentId);
        getAndParseUrl.contentRequest(new GetAndParseUrl.CallbackListener() {
            @Override
            public void onFinish(Object object) {
                Article article = (Article) object;
                String temp = article.contents.get(0).content;
                Document doc = Jsoup.parse(temp);
                Elements elements = doc.select("img[src]");
                for (Element e : elements) {
                    e.attr("style", "width:100%;height:auto");
                    e.removeAttr("alt");
                }
                final String data = doc.outerHtml();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //加载内容html
                        webview.loadDataWithBaseURL("http://api.acfun.tv/apiserver/content/article?contentId=" + contentId, data, "text/html", "UTF-8", null);
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });


        //设置Web视图
        webview.setWebViewClient(new webViewClient());


        return mView;
    }



    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
