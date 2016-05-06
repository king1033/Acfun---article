package acfun.com.article.API;

import java.util.Locale;


/**
 *
 */
public class UrlApi {

    public static final String BASE_URL = "http://api.acfun.tv/";

    //文章 63
    //文章列表 - 综合 110
    public static String getListUrl(int order, int channelId, int page){
        int count = 10;
        return String.format(Locale.US, "http://api.acfun.tv/apiserver/content/channel?orderBy=%d&channelId=%d&pageSize=%d&pageNo=%d", order, channelId, count, page);
    }

    //文章内容
    public static String getArticleUrl(int contentId){
        return String.format((Locale.US),"http://api.acfun.tv/apiserver/content/article?contentId=%d",contentId);
    }



}
