package acfun.com.article;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import acfun.com.article.util.GetAndParseHTML;

/**
 *
 */
public class ArticleFragment extends Fragment {

    private WebView webview;
    private View mView;
    private int contentId;
    private String htmlData;
    private MainActivity mainActivity;

    public static ArticleFragment newInstance(int contentId, String htmlData){
        Bundle args = new Bundle();
        args.putInt("contentId", contentId);
        args.putString("htmlData", htmlData);
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
        contentId = getArguments().getInt("contentId");
        htmlData = getArguments().getString("htmlData");
        mainActivity = (MainActivity) getActivity();
        mainActivity.changeFab();

        webview = (WebView) mView.findViewById(R.id.web_view);
        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(false);


        //加载内容html
        webview.loadDataWithBaseURL("http://m.acfun.tv/v/?ac=" + contentId, htmlData ,"text/html", "UTF-8", null);


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
