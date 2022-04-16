package com.example.smartcitytestv1.park.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.park.beans.ParkRecord;

import java.util.List;

public class ParkRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<ParkRecord> parkRecords;
    private RecyclerView.ViewHolder holder;

    public ParkRecordAdapter(int resourceId, List<ParkRecord> parkRecords) {
        this.resourceId = resourceId;
        this.parkRecords = parkRecords;
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
        ParkRecord parkRecord = parkRecords.get(position);
        View itemView = holder.itemView;

        TextView plate = (TextView) itemView.findViewById(R.id.record_plate);
        TextView entryTime = (TextView) itemView.findViewById(R.id.entry_time);
        TextView outTime = (TextView) itemView.findViewById(R.id.out_time);
        TextView address  = (TextView) itemView.findViewById(R.id.record_address);
        TextView pay = (TextView) itemView.findViewById(R.id.record_pay);

        plate.setText(parkRecord.getPlateNumber());
        entryTime.setText(parkRecord.getEntryTime());
        outTime.setText(parkRecord.getOutTime());
        address.setText(parkRecord.getParkName());
        pay.setText("消费"+parkRecord.getMonetary()+"元");

    }

    @Override
    public int getItemCount() {
        return parkRecords.size();
    }
}
