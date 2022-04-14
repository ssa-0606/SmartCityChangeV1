package com.example.smartcitytestv1.ui.home.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.ui.home.beans.NewsItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<NewsItem> newsItemList;
    private RecyclerView.ViewHolder holder;

    public NewsAdapter(int resourceId, List<NewsItem> newsItemList) {
        this.resourceId = resourceId;
        this.newsItemList = newsItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, null);
        holder = new RecyclerView.ViewHolder(view) {};
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NewsItem newsItem = newsItemList.get(position);
        View view = holder.itemView;

        ImageView imageView = (ImageView) view.findViewById(R.id.news_img);
        TextView title = (TextView) view.findViewById(R.id.news_tit);
        TextView content = (TextView) view.findViewById(R.id.news_content);
        TextView publish = (TextView) view.findViewById(R.id.news_publish_date);
        TextView comment = (TextView) view.findViewById(R.id.news_comment_num);

        Glide.with(view).load(newsItem.getCover()).into(imageView);
        title.setText(newsItem.getTitle());
        content.setText(Html.fromHtml(newsItem.getContent()));
        publish.setText(newsItem.getPublishDate());
        comment.setText(String.valueOf(newsItem.getCommentNum()));

    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }
}
