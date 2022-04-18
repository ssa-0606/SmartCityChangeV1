package com.example.smartcitytestv1.metro.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.metro.MetroDetailActivity;
import com.example.smartcitytestv1.metro.beans.MetroLines;

import java.util.List;

public class MetroLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceId;
    private List<MetroLines> metroLines;
    private RecyclerView.ViewHolder holder;

    public MetroLineAdapter(int resourceId, List<MetroLines> metroLines) {
        this.resourceId = resourceId;
        this.metroLines = metroLines;
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
        MetroLines metroLines = this.metroLines.get(position);
        View itemView = holder.itemView;

        TextView lineName = (TextView) itemView.findViewById(R.id.metro_line_name);
        TextView nextStep = (TextView) itemView.findViewById(R.id.metro_line_next);
        TextView reachTime = (TextView) itemView.findViewById(R.id.metro_line_reach);
        LinearLayout linearLayout = (LinearLayout) itemView.findViewById(R.id.metro_to_detail);

        lineName.setText(metroLines.getLineName());
        nextStep.setText(metroLines.getNextStep());
        reachTime.setText(String.valueOf(metroLines.getReachTime()));
        linearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(itemView.getContext(), MetroDetailActivity.class);
            intent.putExtra("lineId",metroLines.getLineId());
            itemView.getContext().startActivity(intent);
        });
        
    }

    @Override
    public int getItemCount() {
        return metroLines.size();
    }
}
