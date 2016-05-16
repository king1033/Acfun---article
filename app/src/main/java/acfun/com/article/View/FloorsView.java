package acfun.com.article.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
        final int i = getChildCount() - 1;

        if (i > 0) {
            View itemView = getChildAt(i);

            ColorDrawable drawable = new ColorDrawable(0xFFFFFEEE);
            drawable.setBounds(itemView.getLeft(), 0,
                    itemView.getRight(), itemView.getBottom());
            drawable.draw(canvas);

        }


        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime){
        Drawable border = ContextCompat.getDrawable(getContext(), R.drawable.floors_item);
        border.setBounds(child.getLeft(), child.getLeft(),
                child.getRight(), child.getBottom());
        border.draw(canvas);
        return super.drawChild(canvas, child, drawingTime);
    }

    public void setCommentView(List<View> commentList) {


        int j = 0;
        final int offset = 6;
        int i = commentList.size() - 1;

        View lastItem = commentList.get(0);
        if (lastItem.getParent() != null){
            ((ViewGroup)lastItem.getParent()).removeView(lastItem);
        }
        CommentsAdapter.LastCommentViewHolder holder = (CommentsAdapter.LastCommentViewHolder) lastItem.getTag();
        setLastViewText(holder, i);
        lastItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentsAdapter.LastCommentViewHolder holder = (CommentsAdapter.LastCommentViewHolder) v.getTag();
                if (holder.isShowing) {
                    holder.isShowing = false;
                    setLastViewText(holder, getChildCount() - 1 );

                    int scroll = v.getTop();
                    ((View)getParent().getParent()).scrollBy(0, -scroll);
                } else {
                    holder.isShowing = true;
                    setLastViewText(holder, getChildCount() - 1 );
                }
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
            if (item.getParent() != null){
                ((ViewGroup)item.getParent()).removeView(item);
            }
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
