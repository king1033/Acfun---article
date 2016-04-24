package acfun.com.article.ArticleTextViewFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import acfun.com.article.MainActivity;
import acfun.com.article.R;
import acfun.com.article.util.GetAndParseUrl;

/**
 *
 */
public class ArticleTextViewFragment extends Fragment {

    private View mView;
    private Handler handler;
    private HtmlTextView textView;

    private MainActivity mainActivity;

    private int contentId;


    public static ArticleTextViewFragment newInstance(int contentId){
        Bundle args = new Bundle();
        args.putInt("contentId", contentId);
        ArticleTextViewFragment fragment = new ArticleTextViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_textview, container, false);
        handler = new Handler();
        contentId = getArguments().getInt("contentId");

        mainActivity = (MainActivity) getActivity();
        mainActivity.changeFab();

        textView = (HtmlTextView) mView.findViewById(R.id.test_textView);






        GetAndParseUrl getAndParseUrl = new GetAndParseUrl("http://api.acfun.tv/apiserver/content/article?contentId=" + contentId);
        getAndParseUrl.contentRequest(new GetAndParseUrl.CallbackListener() {
            @Override
            public void onFinish(Object object) {
                final String data = (String) object;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //加载内容html
                        textView.setHtmlFromString(data, false);

                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });

        return mView;
    }


    public class UrlImageGetter implements Html.ImageGetter {
        Context c;
        TextView container;
        int width;

        /**
         * @param t
         * @param c
         */
        public UrlImageGetter(TextView t, Context c) {
            this.c = c;
            this.container = t;
            width = c.getResources().getDisplayMetrics().widthPixels;
        }

        @Override
        public Drawable getDrawable(String source) {
            final UrlDrawable urlDrawable = new UrlDrawable();
            ImageLoader.getInstance().loadImage(source,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view,
                                                      Bitmap loadedImage) {
                            // ??????
                            float scaleWidth = ((float) width) / loadedImage.getWidth();

                            // ???????matrix??
                            Matrix matrix = new Matrix();
                            matrix.postScale(scaleWidth, scaleWidth);
                            loadedImage = Bitmap.createBitmap(loadedImage, 0, 0,
                                    loadedImage.getWidth(), loadedImage.getHeight(),
                                    matrix, true);
                            urlDrawable.bitmap = loadedImage;
                            urlDrawable.setBounds(0, 0, loadedImage.getWidth(),
                                    loadedImage.getHeight());
                            container.invalidate();
                            container.setText(container.getText()); // ??????
                        }
                    });

            return urlDrawable;
        }

        @SuppressWarnings("deprecation")
        public class UrlDrawable extends BitmapDrawable {
            protected Bitmap bitmap;

            @Override
            public void draw(Canvas canvas) {
                // override the draw to facilitate refresh function later
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, 0, 0, getPaint());
                }
            }
        }
    }
}
