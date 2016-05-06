package acfun.com.article.API;

import acfun.com.article.entity.Artic;
import acfun.com.article.entity.TitlesList;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 *
 */
public interface ApiService {

    @GET()
    Observable<TitlesList> RxGetTitlesList(@Url String url);

    @GET()
    Observable<Artic> RxGetArticle(@Url String url);

}
