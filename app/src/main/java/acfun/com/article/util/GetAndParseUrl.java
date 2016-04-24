package acfun.com.article.util;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import acfun.com.article.entity.Article;
import acfun.com.article.entity.ArticleTitle;
import acfun.com.article.entity.Pages;

/**
 *
 */
public class GetAndParseUrl {
    private String address;

    public GetAndParseUrl(String url){
        this.address = url;
    }

    public void pagesRequest(final CallbackListener listener){
        sendHttpRequest(listener, "pages");
    }

    public void contentRequest(final CallbackListener listener){
        sendHttpRequest(listener, "content");
    }



    protected void sendHttpRequest (final CallbackListener listener, final String type){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;                                      //HttpURLConnection
                try {
                    URL url = new URL(address);                   //指定访问的服务器地址
                    connection = (HttpURLConnection) url.openConnection();                 //获取一个HttpURLConnection实例
                    connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
                    connection.setRequestMethod("GET");                        //GET表示希望从服务器获取数据   POST表示希望提交数据给服务器
                    connection.setConnectTimeout(8000);                                                 //连接超时的毫秒数
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();                                       //获取服务器返回的输入流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));              //将输入流转换成String
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        if ("pages".equals(type)) {
                            listener.onFinish(Json2Pages(response.toString()));
                        }
                        else if ("content".equals(type)){
                            listener.onFinish(Json2Html(response.toString()));
                        }
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                }
            }
        }).start();
    }

    protected Pages Json2Pages (String response){
        JSONObject rankList = JSON.parseObject(response);

        JSONObject page = rankList.getJSONObject("data").getJSONObject("page");
        JSONArray jsonArr = page.getJSONArray("list");

        List<ArticleTitle> articleTitles = new ArrayList<>();
        for(int i=0;i<jsonArr.size();i++){
            JSONObject carr = jsonArr.getJSONObject(i);
            ArticleTitle title = new ArticleTitle();

            title.setUserName(carr.getJSONObject("user").getString("username"));
            title.setStows(carr.getIntValue("stows"));
            title.setComments(carr.getIntValue("comments"));
            title.setViews(carr.getIntValue("views"));
            title.setTitle(carr.getString("title"));
            title.setContentId(carr.getIntValue("contentId"));
            title.setDescription(carr.getString("description"));
            articleTitles.add(title);
        }
        Pages pages = new Pages();
        pages.setArticleTitles(articleTitles);
        return pages;
    }

    protected Article Json2Html (String response){
        JSONObject jsonObject = JSON.parseObject(response);

        JSONObject content = jsonObject.getJSONObject("data").getJSONObject("fullArticle");
        Article article = Article.newArticle(content);
        return article;
    }


    public interface CallbackListener {
        void onFinish(Object object);

        void onError (Exception e);
    }

}
