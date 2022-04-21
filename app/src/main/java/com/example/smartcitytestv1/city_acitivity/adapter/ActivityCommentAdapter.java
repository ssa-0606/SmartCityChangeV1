package com.example.smartcitytestv1.city_acitivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.city_acitivity.beans.CommentUser;

import java.util.List;

public class ActivityCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<CommentUser> commentUserList;
    private RecyclerView.ViewHolder holder;

    public ActivityCommentAdapter(int resourceId, List<CommentUser> commentUserList) {
        this.resourceId = resourceId;
        this.commentUserList = commentUserList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(resourceId, null);
        holder = new RecyclerView.ViewHolder(inflate) {};
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentUser commentUser = commentUserList.get(position);
        View itemView = holder.itemView;
        ImageView imageView = itemView.findViewById(R.id.comment_avatar);
        TextView nick = itemView.findViewById(R.id.comment_nick);
        TextView content = itemView.findViewById(R.id.comment_content);
        TextView time = itemView.findViewById(R.id.comment_time);
        Glide.with(itemView).load(commentUser.getAvatar()).into(imageView);
        nick.setText(commentUser.getNickName());
        content.setText(commentUser.getContent());
        time.setText(commentUser.getCommentTime());
    }

    @Override
    public int getItemCount() {
        return commentUserList.size();
    }
}
