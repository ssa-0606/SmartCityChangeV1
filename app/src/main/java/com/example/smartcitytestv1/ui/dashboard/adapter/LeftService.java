package com.example.smartcitytestv1.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.ui.home.beans.CityService;

import java.util.ArrayList;
import java.util.List;

public class LeftService extends ArrayAdapter<String> {

    private int resourceId;


    public LeftService(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = getItem(position);
        View inflate = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView textView = inflate.findViewById(R.id.left_service);
        textView.setText(item);
        return inflate;
    }
}
