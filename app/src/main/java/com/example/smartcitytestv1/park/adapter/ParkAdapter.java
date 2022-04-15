package com.example.smartcitytestv1.park.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.park.ParkDetailActivity;
import com.example.smartcitytestv1.park.beans.Park;

import java.util.List;

public class ParkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<Park> parkList;
    private RecyclerView.ViewHolder holder;

    public ParkAdapter(int resourceId, List<Park> parkList) {
        this.resourceId = resourceId;
        this.parkList = parkList;
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
        Park park = parkList.get(position);
        View itemView = holder.itemView;
        TextView name = (TextView) itemView.findViewById(R.id.park_name);
        TextView distance = (TextView) itemView.findViewById(R.id.park_distance);
        TextView vacancy = (TextView) itemView.findViewById(R.id.park_vacancy);
        TextView rates = (TextView) itemView.findViewById(R.id.park_rates);
        TextView address = (TextView) itemView.findViewById(R.id.park_address);

        LinearLayout layout = (LinearLayout) itemView.findViewById(R.id.part_item);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ParkDetailActivity.class);
                intent.putExtra("park_id",park.getId());
                view.getContext().startActivity(intent);
            }
        });

        name.setText(park.getParkName());
        distance.setText(park.getDistance()+"km");
        vacancy.setText(park.getVacancy());
        rates.setText(park.getRates()+"元/小时");
        address.setText(park.getAddress());
    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }
}
