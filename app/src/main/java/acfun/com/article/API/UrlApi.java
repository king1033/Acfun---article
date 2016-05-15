package acfun.com.article.API;

import java.util.Locale;


/**
 *
 */
public class UrlApi {

    public static final String BASE_URL = "http://api.acfun.tv/";
    public static final String OFFICIAL_URL = "http://api.aixifan.com/";

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

    //评论
    public static String getCommentUrl(int contentId, int page){
        page = 1;
        return String.format((Locale.US),"http://www.acfun.tv/comment_list_json.aspx?contentId=%d&currentPage=%d", contentId, page);
    }


}
