package acfun.com.article;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import acfun.com.article.View.FloorsView;
import acfun.com.article.entity.Comment;


/**
 *
 */
public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;

    private Context context;
    private LayoutInflater inflater;

    private List<Integer> commentIdList;
    private SparseArray<Comment> data;


    public CommentsAdapter(Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView userImg;
        TextView userName;
        TextView time;
        TextView count;
        TextView content;
        Boolean hasFrame;
        FloorsView floorFrame;
        public CommentViewHolder (final View itemView){
            super(itemView);

            userImg = (SimpleDraweeView) itemView.findViewById(R.id.comment_user_img);
            userName = (TextView)itemView.findViewById(R.id.comment_username);
            time = (TextView)itemView.findViewById(R.id.comment_time);
            count = (TextView)itemView.findViewById(R.id.comment_count);
            content = (TextView) itemView.findViewById(R.id.comment_comment);
            floorFrame = (FloorsView) itemView.findViewById(R.id.floors_view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            return new CommentViewHolder(inflater.inflate(R.layout.comment_rv_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder temp, final int position) {
        CommentViewHolder holder = (CommentViewHolder)temp;
        Comment comment = getItem(position);

        if (comment.getNameRed() == 1){
            holder.userName.setTextColor(Color.RED);
        }
        holder.userName.setText(comment.getUserName());
        holder.time.setText(comment.getPostDate());
        holder.count.setText("#" + comment.getCount());
        holder.content.setText(Html.fromHtml(comment.getContent()));
        holder.userImg.setImageURI(Uri.parse(comment.getUserImg()));
        holder.hasFrame = comment.getQuoteId() > 0;
        holder.floorFrame.removeAllViews();
        if (holder.hasFrame){

            holder.floorFrame.setCommentView(getViewList(comment));
        }

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return commentIdList==null? 0 : commentIdList.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    public void getData(List<Integer> list, SparseArray<Comment> data){
        this.commentIdList = list;
        this.data = data;
    }


    public Comment getItem(int position){
        try {
            Integer id = commentIdList.get(position);
            if (id != null)
                return data.get(id);
        } catch (IndexOutOfBoundsException e) {}
        return null;
    }

    private ArrayList<View> getViewList(Comment comment){
        int quoteId = comment.getQuoteId();
        ArrayList<View> viewList = new ArrayList<>();
        View lastView = inflater.inflate(R.layout.comments_view_show,null);
        LastCommentViewHolder holder = new LastCommentViewHolder();
        holder.lastContent = (TextView) lastView.findViewById(R.id.comments_last);
        holder.lastShow = (TextView) lastView.findViewById(R.id.comments_last_show);

        holder.isShowing = true;
        if (comment.getRefCount() > 0 ){
            holder.isShowing = false;
            holder.isRef = true;
        }
        lastView.setTag(holder);
        viewList.add(lastView);

        for (Comment quote = data.get(quoteId); quote != null ;quoteId = quote.getQuoteId(), quote = data.get(quoteId)) {
            View itemView = inflater.inflate(R.layout.comments_view_item, null);
            TextView userName = (TextView) itemView.findViewById(R.id.user_name);
            TextView content = (TextView) itemView.findViewById(R.id.comments_content);
            TextView floor = (TextView) itemView.findViewById(R.id.floor);
            if (quote.getNameRed() == 1){
                userName.setTextColor(Color.RED);
            }
            userName.setText(quote.getUserName());
            content.setText(Html.fromHtml(quote.getContent()));
            floor.setText("#" + quote.getCount());
            viewList.add(itemView);
        }

        return viewList;
    }

    public static class LastCommentViewHolder {
        public Boolean isShowing = false;
        public Boolean isRef = false;
        public TextView lastContent;
        public TextView lastShow;
    }
}
