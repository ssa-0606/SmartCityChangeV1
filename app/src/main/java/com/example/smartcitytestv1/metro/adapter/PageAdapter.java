package com.example.smartcitytestv1.metro.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.metro.beans.MetroPage;

import java.util.List;
import java.util.Random;

public class PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<MetroPage> metroPages;
    private RecyclerView.ViewHolder holder;

    public PageAdapter(int resourceId, List<MetroPage> metroPages) {
        this.resourceId = resourceId;
        this.metroPages = metroPages;
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
        MetroPage metroPage = metroPages.get(position);
        View itemView = holder.itemView;
        TextView textView = (TextView) itemView.findViewById(R.id.metro_page_text);
        textView.setText(metroPage.getLineName());
        Random random = new Random();
        textView.setTextColor(Color.rgb(random.nextInt(150),random.nextInt(150),random.nextInt(150)));
    }

    @Override
    public int getItemCount() {
        return metroPages.size();
    }
}
