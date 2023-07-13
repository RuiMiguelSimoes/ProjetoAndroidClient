package com.example.projeto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> comments) {
        this.commentList = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.commentContentTextView.setText(comment.getCommentContent());
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
        private TextView commentContentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentContentTextView = itemView.findViewById(R.id.commentTextView);
        }

        public void bind(Comment comment) {
            commentContentTextView.setText(comment.getCommentContent());
        }
    }
}
