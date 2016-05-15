package acfun.com.article.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import acfun.com.article.CommentsAdapter;
import acfun.com.article.R;

/**
 *
 */
public class FloorsView extends LinearLayout {

    private int mMaxNum = 4;

    public FloorsView(Context context) {
        this(context, null);
    }

    public FloorsView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onLayout(boolean changed, int x, int y, int oX, int oY){
        super.onLayout(changed, x, y, oX, oY);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int i = getChildCount();
        Drawable border = ContextCompat.getDrawable(getContext(), R.drawable.floors_item);

        for (int j = i - 1; j >= 0; j--){
            View itemView = getChildAt(j);
            if (j == i - 1){
                ColorDrawable drawable = new ColorDrawable(0xFFFFFEEE);
                drawable.setBounds(itemView.getLeft(), itemView.getLeft(),
                        itemView.getRight(), itemView.getBottom());
                drawable.draw(canvas);
            }
            border.setBounds(itemView.getLeft(), itemView.getLeft(),
                    itemView.getRight(), itemView.getBottom());
            border.draw(canvas);

        }
        super.dispatchDraw(canvas);
    }


    public void setCommentView(List<View> commentList) {
        int j = 0;
        final int offset = 6;
        int i = commentList.size() - 1;

        View lastItem = commentList.get(0);
        CommentsAdapter.LastCommentViewHolder holder = (CommentsAdapter.LastCommentViewHolder) lastItem.getTag();
        setLastViewText(holder, i);
        lastItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutParams params = generateDefaultLayoutParams();
                CommentsAdapter.LastCommentViewHolder holder = (CommentsAdapter.LastCommentViewHolder) v.getTag();
                if (holder.isShowing) {
                    holder.isShowing = false;
                    setLastViewText(holder, getChildCount() - 1 );
                    params.topMargin = offset * (getChildCount() - 1);
                } else {
                    holder.isShowing = true;
                    setLastViewText(holder, getChildCount() - 1 );
                    params.topMargin = 0;
                }
                v.setLayoutParams(params);
                for (int i = 0; i < getChildCount() - 1; i++) {
                    View itemView = getChildAt(i);
                    if (!holder.isShowing) {
                        itemView.setVisibility(GONE);
                    } else {
                        itemView.setVisibility(VISIBLE);
                    }
                }
            }
        });

        for (; i > 0; i--) {
            LayoutParams params = generateDefaultLayoutParams();
            int k = offset * i;

            if (commentList.size() > mMaxNum + 2 && i > mMaxNum) {
                k = offset * mMaxNum;
            }
            params.leftMargin = k;
            params.rightMargin = k;
            params.topMargin = j == 0 ? k : 0;
            View item = commentList.get(i);
            item.setVisibility(GONE);

            if (holder.isShowing) {
                item.setVisibility(VISIBLE);
            }
            addView(item, j++, params);
        }




        addView(lastItem);


    }

    public void setLastViewText(CommentsAdapter.LastCommentViewHolder holder, int i) {
        if (holder.isRef){
            if (holder.isShowing) {
                holder.lastContent.setText("重复引用已显示 ");
                holder.lastShow.setText("[收起] ∧");
            } else {
                holder.lastContent.setText("重复引用已隐藏 ");
                holder.lastShow.setText("[展开] ∨");
            }

        }else {
            if (holder.isShowing) {
                holder.lastContent.setText(i + "层引用已显示 ");
                holder.lastShow.setText("[收起] ∧");
            } else {
                holder.lastContent.setText(i + "层引用已隐藏 ");
                holder.lastShow.setText("[展开] ∨");
            }
        }
    }
}
