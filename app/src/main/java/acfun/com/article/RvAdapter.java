package acfun.com.article;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import acfun.com.article.entity.Title;

/**
 *
 */
//recyclerView适配器
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;

    private Context context;
    private List<Title> mTitles;

    private LayoutInflater inflater;
    private int fit;

    public RvAdapter(LayoutInflater inflater, Context context, int fit){
        mTitles = new ArrayList<>();
        this.context = context;
        this.inflater = inflater;
        this.fit = fit;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mUserName;
        TextView mDescription;
        TextView mComments;
        TextView mTime;
        TextView mStows;
        TextView mViewer;
        public NormalViewHolder(final View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.tv_item_title);
            mUserName = (TextView)itemView.findViewById(R.id.tv_item_username);
            mDescription = (TextView)itemView.findViewById(R.id.tv_item_description);
            mComments = (TextView)itemView.findViewById(R.id.tv_item_comments);
            mTime = (TextView)itemView.findViewById(R.id.tv_item_time);
            mStows = (TextView)itemView.findViewById(R.id.tv_item_stows);
            mViewer = (TextView)itemView.findViewById(R.id.tv_item_viewer);
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
        holder.mTitle.setText(Html.fromHtml(mTitle.getTitle()));
        holder.mUserName.setText(mTitle.getUser().getUsername());
        holder.mDescription.setText(mTitle.getDescription());
        if (mTitle.getComments() < 999) {
            holder.mComments.setText(Integer.toString(mTitle.getComments()));
        }else {
            holder.mComments.setText("999");
        }
        holder.mTime.setText(parseTimeData(mTitle.getReleaseDate()));
        holder.mStows.setText(Integer.toString(mTitle.getStows()));
        holder.mViewer.setText(Integer.toString(mTitle.getViews()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title temp = mTitles.get(holder.getAdapterPosition() - fit);
                TestActivity.start(context, temp.getContentId(), temp.getTitle());
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

    public String parseTimeData(long postTime){
        final long _1_min = 60 * 1000;
        final long _1_hour = 60 * _1_min;
        final long _24_hour = 24 * _1_hour;
        final long _7_day = 7 * _24_hour;
        final long _1_month = 30 * _24_hour;

        long delta = System.currentTimeMillis() - postTime;
        if( delta < _1_min){
            return "刚刚 ";
        } else if( delta < _1_hour) {
            int time = (int) (delta / _1_min);
            return time + "分钟前 ";
        }else if( delta <  _24_hour){
            int time = (int) (delta / _1_hour);
            return time+"小时前 ";
        } else if( delta < _7_day){
            int time = (int) (delta / _24_hour);
            return time+"天前 " ;
        }else if( delta < _1_month){
            return getDateTime("MM-dd kk:mm", postTime);
        }else {
            return getDateTime("yyyy-MM-dd" ,postTime);

        }
    }
    public String getDateTime(CharSequence format, long msec) {
        return DateFormat.format(format, msec).toString();
    }

}
