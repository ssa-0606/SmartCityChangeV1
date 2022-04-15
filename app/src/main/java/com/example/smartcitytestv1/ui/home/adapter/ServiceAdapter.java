package com.example.smartcitytestv1.ui.home.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.MainActivity;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.park.ParkActivity;
import com.example.smartcitytestv1.ui.home.beans.CityService;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int resourceID;
    private List<CityService> serviceList;
    private RecyclerView.ViewHolder holder;
    private Activity activity;

    public ServiceAdapter(int resourceID, List<CityService> serviceList,Activity activity) {
        this.resourceID = resourceID;
        this.serviceList = serviceList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(resourceID, null);
        holder = new RecyclerView.ViewHolder(inflate) {};
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CityService service = serviceList.get(position);
        View itemView = holder.itemView;
        ImageView imageView = (ImageView) itemView.findViewById(R.id.service_img);
        TextView textView = (TextView) itemView.findViewById(R.id.service_name);

        if(TextUtils.equals(service.getServiceName(),"全部服务")){
            imageView.setImageResource(R.drawable.ic_dashboard_black_24dp);
            textView.setText(service.getServiceName());
        }else{
            Glide.with(itemView).load(service.getImgUrl()).into(imageView);
            textView.setText(service.getServiceName());
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                if(TextUtils.equals(service.getServiceName(),"全部服务")){
                    NavController navController = Navigation.findNavController(itemView);
                    navController.navigate(R.id.action_navigation_home_to_navigation_dashboard);
                }else if(TextUtils.equals(service.getServiceName(),"停哪儿")){
                    Intent intent = new Intent(itemView.getContext(), ParkActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }
}
