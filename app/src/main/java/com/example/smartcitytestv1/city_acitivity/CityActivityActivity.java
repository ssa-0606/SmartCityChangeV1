package com.example.smartcitytestv1.city_acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.city_acitivity.adapter.ActivityAdapter;
import com.example.smartcitytestv1.city_acitivity.beans.ActivityCategory;
import com.example.smartcitytestv1.city_acitivity.beans.ActivityItem;
import com.example.smartcitytestv1.tools.MyUtils;
import com.example.smartcitytestv1.ui.home.beans.LunBoImg;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityActivityActivity extends AppCompatActivity {

    private ViewFlipper vf;
    private List<LunBoImg> imgs;
    private TabLayout tabLayout;
    private List<ActivityCategory> activityCategories;
    private List<ActivityItem> activityItems;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_activity);

        vf = findViewById(R.id.activity_vf);
        tabLayout = findViewById(R.id.activity_tab);
        recyclerView = (RecyclerView) findViewById(R.id.activity_recycler);

        getLunBo();
        getCategories();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Message message = new Message();
                message.obj = tab.getPosition();
                message.what = 3;
                handler.sendMessage(message);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void getCategories() {
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/activity/category/list");
                JSONArray data = new JSONObject(result).getJSONArray("data");
                activityCategories = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    int sort = jsonObject.getInt("sort");
                    String items = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/activity/activity/list?categoryId="+id);
                    activityItems = new ArrayList<>();
                    JSONArray rows = new JSONObject(items).getJSONArray("rows");
                    for (int j = 0; j < rows.length(); j++) {
                        JSONObject jsonObject1 = rows.getJSONObject(j);
                        int id1 = jsonObject1.getInt("id");
                        String name1 = jsonObject1.getString("name");
                        String content = jsonObject1.getString("content");
                        String imgUrl = jsonObject1.getString("imgUrl");
                        int signupNum = jsonObject1.getInt("signupNum");
                        int likeNum = jsonObject1.getInt("likeNum");
                        String status = jsonObject1.getString("status");
                        String publishTime = jsonObject1.getString("publishTime");
                        activityItems.add(new ActivityItem(id1,name1,content,imgUrl,signupNum,likeNum,status,publishTime));
                    }
                    activityCategories.add(new ActivityCategory(id,name,sort,activityItems));
                }

                handler.sendEmptyMessage(2);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void getLunBo() {
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/activity/rotation/list");
                JSONArray rows = new JSONObject(result).getJSONArray("rows");
                imgs = new ArrayList<>();
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject jsonObject = rows.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String advImg = jsonObject.getString("advImg");
                    int targetId = jsonObject.getInt("targetId");
                    imgs.add(new LunBoImg(id,advImg,targetId));
                }
                handler.sendEmptyMessage(1);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    for (LunBoImg img : imgs) {
                        ImageView imageView = new ImageView(getBaseContext());
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(getBaseContext()).load("http://124.93.196.45:10001"+img.getAdvImg()).into(imageView);
                        vf.addView(imageView);
                    }
                    break;
                case 2:
                    for (ActivityCategory activityCategory : activityCategories) {
                        tabLayout.addTab(tabLayout.newTab().setText(activityCategory.getName()).setTag(activityCategory.getId()));
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerView.setAdapter(new ActivityAdapter(R.layout.my_activity_list,activityCategories.get(0).getActivityItems()));
                    break;
                case 3:
                    int i = (int)msg.obj;
                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerView.setAdapter(new ActivityAdapter(R.layout.my_activity_list,activityCategories.get(i).getActivityItems()));
                    break;
            }
        }
    };


}