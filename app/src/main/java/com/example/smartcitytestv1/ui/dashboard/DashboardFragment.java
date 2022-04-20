package com.example.smartcitytestv1.ui.dashboard;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.databinding.FragmentDashboardBinding;
import com.example.smartcitytestv1.tools.MyUtils;
import com.example.smartcitytestv1.ui.dashboard.adapter.LeftService;
import com.example.smartcitytestv1.ui.home.adapter.ServiceAdapter;
import com.example.smartcitytestv1.ui.home.beans.CityService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;


    private List<CityService> serviceList;
    private List<String> serviceTypeList;
    private ListView recyclerView;
    private RecyclerView serviceAll;
    private List<CityService> cityServices;
    private List<CityService> shenghuo;
    private List<CityService> bianmin;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container,false);

        recyclerView = view.findViewById(R.id.service_detail_type);
        serviceAll = view.findViewById(R.id.service_all);

        getService();

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    serviceAll.setLayoutManager(new GridLayoutManager(getContext(),3));
                    serviceAll.setAdapter(new ServiceAdapter(R.layout.service_all_layout,shenghuo));
                }else if(i==2){
                    serviceAll.setLayoutManager(new GridLayoutManager(getContext(),3));
                    serviceAll.setAdapter(new ServiceAdapter(R.layout.service_all_layout,bianmin));
                }else{
                    serviceAll.setLayoutManager(new GridLayoutManager(getContext(),3));
                    serviceAll.setAdapter(new ServiceAdapter(R.layout.service_all_layout,cityServices));
                }
            }
        });

        return view;
    }

    public void getService(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/service/list");
                JSONObject resultJson = new JSONObject(result);
                JSONArray rows = resultJson.getJSONArray("rows");
                serviceList = new ArrayList<>();
                serviceTypeList = new ArrayList<>();
                cityServices = new ArrayList<>();
                shenghuo = new ArrayList<>();
                bianmin = new ArrayList<>();
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject data = rows.getJSONObject(i);
                    int id = data.getInt("id");
                    String serviceName = data.getString("serviceName");
                    String serviceType = data.getString("serviceType");
                    String imgUrl = data.getString("imgUrl");
                    int sort = data.getInt("sort");
                    if(!serviceTypeList.contains(serviceType)){
                        serviceTypeList.add(serviceType);
                    }
                    serviceList.add(new CityService(id,serviceName,serviceType,"http://124.93.196.45:10001"+imgUrl,sort));
                    if(TextUtils.equals(serviceType,"车主服务")){
                        cityServices.add(new CityService(id,serviceName,serviceType,"http://124.93.196.45:10001"+imgUrl,sort));
                    }else if(TextUtils.equals(serviceType,"生活服务")){
                        shenghuo.add(new CityService(id,serviceName,serviceType,"http://124.93.196.45:10001"+imgUrl,sort));
                    }else {
                        bianmin.add(new CityService(id,serviceName,serviceType,"http://124.93.196.45:10001"+imgUrl,sort));
                    }
                }
                getActivity().runOnUiThread(()->{
                    recyclerView.setAdapter(new LeftService(getActivity(),R.layout.service_left_layout,serviceTypeList));
                    serviceAll.setLayoutManager(new GridLayoutManager(getContext(),3));
                    serviceAll.setAdapter(new ServiceAdapter(R.layout.service_all_layout,cityServices));
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}