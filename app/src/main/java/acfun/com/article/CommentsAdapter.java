package acfun.com.article;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

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
        Boolean setImg = false;
        SimpleDraweeView userImg;
        TextView userName;
        TextView time;
        TextView count;
        TextView content;
        public CommentViewHolder (final View itemView){
            super(itemView);
            userImg = (SimpleDraweeView) itemView.findViewById(R.id.comment_user_img);
            userName = (TextView)itemView.findViewById(R.id.comment_username);
            time = (TextView)itemView.findViewById(R.id.comment_time);
            count = (TextView)itemView.findViewById(R.id.comment_count);
            content = (TextView) itemView.findViewById(R.id.comment_comment);
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
        holder.userName.setText(comment.getUserName());
        holder.time.setText(comment.getPostDate());
        holder.count.setText("#" + comment.getCount());
        holder.content.setText(Html.fromHtml(comment.getContent()));
        holder.userImg.setImageURI(Uri.parse(comment.getUserImg()));
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
}
