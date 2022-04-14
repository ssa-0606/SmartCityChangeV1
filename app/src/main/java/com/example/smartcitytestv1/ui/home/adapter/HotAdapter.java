package com.example.smartcitytestv1.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.ui.home.beans.CityService;

import java.util.List;

public class HotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<CityService> cityServices;
    private RecyclerView.ViewHolder holder;

    public HotAdapter(int resourceId, List<CityService> cityServices) {
        this.resourceId = resourceId;
        this.cityServices = cityServices;
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
        CityService service = cityServices.get(position);
        View view = holder.itemView;
        ImageView imageView = (ImageView) view.findViewById(R.id.hot_img);
        TextView textView = (TextView) view.findViewById(R.id.hot_name);

        Glide.with(view).load(service.getImgUrl()).into(imageView);
        textView.setText(service.getServiceName());

    }

    @Override
    public int getItemCount() {
        return cityServices.size();
    }
}
