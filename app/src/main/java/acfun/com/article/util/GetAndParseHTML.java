package acfun.com.article.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import acfun.com.article.entity.Pages;

/**
 *
 */
public class GetAndParseHTML {

    private String address;

    public GetAndParseHTML(String address){
        this.address = address;
    }

    public void sendHTMLRequest(final DataCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("test", address);
                    Document doc = Jsoup.connect(address).get();
                    Elements paragraphs = doc.select("#area-player p");
                    if (listener != null) {
                        listener.onFinish(paragraphs.html());
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }



    public interface DataCallbackListener {
        void onFinish(String result);

        void onError (Exception e);
    }
}


