package acfun.com.article;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import acfun.com.article.entity.Title;

/**
 *
 */
//recyclerView适配器
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;

    public int foot;

    private Context context;
    private List<Title> mTitles;

    private LayoutInflater inflater;

    public RvAdapter(LayoutInflater inflater, Context context){
        mTitles = new ArrayList<>();
        this.context = context;
        this.inflater = inflater;
        foot = 1;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mUserName;
        TextView mDescription;
        TextView mComments;
        public NormalViewHolder(final View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.tv_item_title);
            mUserName = (TextView)itemView.findViewById(R.id.tv_item_username);
            mDescription = (TextView)itemView.findViewById(R.id.tv_item_description);
            mComments = (TextView)itemView.findViewById(R.id.tv_item_comments);
        }
    }

    //在该方法中我们创建一个ViewHolder并返回，ViewHolder必须有一个带有View的构造函数，这个View就是我们Item的根布局，在这里我们使用自定义Item的布局；
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new NormalViewHolder(inflater.inflate(R.layout.rv_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder temp, final int position) {
        final NormalViewHolder holder = (NormalViewHolder) temp;
        Title mTitle = mTitles.get(position);
        holder.mTitle.setText(mTitle.getTitle());
        holder.mUserName.setText(mTitle.getUser().getUsername());
        holder.mDescription.setText(mTitle.getDescription());
        holder.mComments.setText(Integer.toString(mTitle.getComments()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title temp = mTitles.get(holder.getAdapterPosition() - 1);
                ArticleActivity.start(context, temp.getContentId());
            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    public void getTitles(List<Title> titles){
        mTitles.addAll(titles);
    }

    public void clearTitles(){
        mTitles.clear();
    }

}
