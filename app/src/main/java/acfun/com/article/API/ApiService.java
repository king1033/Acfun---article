package acfun.com.article.API;

import junit.framework.Test;

import acfun.com.article.entity.Article;
import acfun.com.article.entity.Comments;
import acfun.com.article.entity.TitlesList;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 *
 */
public interface ApiService {


    @GET("apiserver/content/article")
    Observable<Article> RxGetArticle(@Query("contentId") int contentId);

    @GET()
    Observable<Comments> RxGetComments(@Url String url);


    //sort=1&pageNo=1&pageSize=6&recommendSize=6&channelIds=110&range=86400000
    //sort:
    // 1 最多观看
    // 2 最多评论
    // 3 最多收藏
    // 4 最新发布
    // 5 最新回复
    @GET("searches/channel")
    Observable<TitlesList> GetTitleList(@Header("deviceType") int deviceType, @Query("sort") int sort, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize,
                               @Query("recommendSize") int recommendSize, @Query("channelIds")int channelIds, @Query("range") int range);

}
