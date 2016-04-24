package acfun.com.article;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import acfun.com.article.ArticleTextViewFragment.ArticleTextViewFragment;
import acfun.com.article.entity.ArticleTitle;

/**
 *
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;                             //!!!!!!!!!!!!!!!!!!!


/*    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }                                                                     //监听器接口

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }                                                                     //!!!!!!!!!!!!!!!!!!!*/

    private MainActivity mainActivity;
    private FragmentTransaction transaction;

    private Context mContext;
    private List<ArticleTitle> mTitles;

    public RvAdapter(Context context, Activity activity, List<ArticleTitle> titleList){
        mTitles = titleList;
        mContext = context;
        mainActivity = (MainActivity) activity;
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mUserName;
        TextView mDescription;
        CardView mCardView;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.tv_item_title);
            mUserName = (TextView)itemView.findViewById(R.id.tv_item_username);
            mDescription = (TextView)itemView.findViewById(R.id.tv_item_description);
            mCardView = (CardView)itemView.findViewById(R.id.cv_item);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View footView) {
            super(footView);
        }
    }

    //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_item, parent, false));
        }else if (viewType == TYPE_FOOTER) {
            return new FootViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_item_foot, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder temp, int position) {
        if (temp instanceof  NormalViewHolder) {
            final NormalViewHolder holder = (NormalViewHolder) temp;
            holder.mTitle.setText(mTitles.get(position).getTitle());
            holder.mUserName.setText(mTitles.get(position).getUserName());
            holder.mDescription.setText(mTitles.get(position).getDescription());


/*            //设置监听器接口
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                    /*transaction.replace(R.id.main_fragment_container, ArticleTextViewFragment.newInstance(mTitles.get(position).getContentId()));
                    transaction.addToBackStack(null);
                    transaction.commit();*/

                    transaction.replace(R.id.main_fragment_container, ArticleFragment.newInstance(mTitles.get(position).getContentId()));
                    transaction.addToBackStack(null);
                    transaction.commit();


                    mainActivity.setState(MainActivity.ARTICLE_FRAGMENT);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    Log.d("test", "item long position = " + position);
                    return true;
                }
            });

        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }
}