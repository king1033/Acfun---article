package acfun.com.article;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import acfun.com.article.entity.FirstImage;

/**
 *
 */
public class NetworkImageHolderView  implements Holder<FirstImage> {
    private View view;
    private SimpleDraweeView simpleDraweeView;
    private TextView textView;
    private GenericDraweeHierarchy hierarchy;

    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        RelativeLayout item = new RelativeLayout(context);
        simpleDraweeView = new SimpleDraweeView(context);
        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        simpleDraweeView.setLayoutParams(imageParams);
        item.addView(simpleDraweeView);
        textView = new TextView(context);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textView.setLayoutParams( textParams );
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(14);
        textView.setPadding(16, 0, 0, 0);
        item.addView(textView);

        hierarchy = new GenericDraweeHierarchyBuilder(context.getResources())
                .setFadeDuration(300)
                .setFailureImage(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
                .build();

        return item;
    }

    @Override
    public void UpdateUI(Context context, int position, FirstImage data) {
        simpleDraweeView.setImageURI(Uri.parse(data.getSrc()));
        textView.setText(data.getText());
        simpleDraweeView.setHierarchy(hierarchy);
    }



}
