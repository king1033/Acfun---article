package acfun.com.article.API;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 *
 */
public class TitleApi {

    //文章 63
    //文章 - 综合 110

    public static String getBaseUrl(int order, int channelId, int count, int page){
        order = 0;
        channelId = 110;
        return String.format(Locale.US, "http://api.acfun.tv/apiserver/content/channel?orderBy=%d&channelId=%d&pageSize=%d&pageNo=%d", order, channelId, count, page);
    }


}
