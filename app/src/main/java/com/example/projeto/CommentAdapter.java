package com.example.projeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.commentList = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        holder.commentContentTextView.setText(comment.getCommentContent());
        holder.commenterTextView.setText(comment.getCommenterName());
        holder.dataTextView.setText(comment.getCommenteDate());
    }

    @Override
    public int getItemCount() {
        if (commentList == null || commentList.isEmpty()) {
            return 0;
        } else {
            return commentList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentContentTextView;
        TextView commenterTextView;
        TextView commentDateTextView;
        TextView dataTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentContentTextView = itemView.findViewById(R.id.commentTextView);
            commenterTextView = itemView.findViewById(R.id.commenterTextView);
            commentDateTextView = itemView.findViewById(R.id.dataTextView);
            dataTextView = itemView.findViewById(R.id.dataTextView);
        }

        public void bind(Comment comment) {
            commentContentTextView.setText(comment.getCommentContent());
        }
    }
}
