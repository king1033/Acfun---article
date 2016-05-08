package acfun.com.article;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;

/**
 *
 */
public class ArticleApp extends Application {

    private static ArticleApp instance;
    private static Context mContext;

    /**
     * <b>NOTE:</b>在 <code>getApplicationContext()</code> 调用一次之后才能用这个方便的方法
     */
    public static ArticleApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = instance = this;
        /*ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);*/
        Fresco.initialize(instance);
    }

    /**
     * 获得缓存目录 <br>
     * <b>NOTE:</b>请先调用  判断是否可用
     *
     * @param type
     *            and so on.
     * @return
     */
    public static File getExternalCacheDir(String type) {
        File cacheDir = new File(mContext.getExternalCacheDir(), type);
        cacheDir.mkdirs();
        return cacheDir;
    }

}
