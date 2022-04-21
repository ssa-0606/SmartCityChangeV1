package com.example.smartcitytestv1.city_acitivity.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.city_acitivity.DetailActivity;
import com.example.smartcitytestv1.city_acitivity.beans.ActivityItem;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<ActivityItem> activityItems;

    private RecyclerView.ViewHolder holder;

    public ActivityAdapter(int resourceId, List<ActivityItem> activityItems) {
        this.resourceId = resourceId;
        this.activityItems = activityItems;
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
        ActivityItem activityItem = activityItems.get(position);
        View itemView = holder.itemView;
        TextView name = itemView.findViewById(R.id.activity_name);
        ImageView imageView = itemView.findViewById(R.id.activity_img);
        TextView sign = itemView.findViewById(R.id.signUp_num);
        TextView publish = itemView.findViewById(R.id.activity_publish);

        name.setText(activityItem.getName());
        Glide.with(itemView).load("http://124.93.196.45:10001"+activityItem.getImgUrl()).into(imageView);
        sign.setText("已报名"+activityItem.getSignupNum()+"人");
        publish.setText(activityItem.getPublishTime());

        CardView cardView = itemView.findViewById(R.id.activity_card);
        cardView.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            intent.putExtra("id",activityItem.getId());
            itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return activityItems.size();
    }
}
