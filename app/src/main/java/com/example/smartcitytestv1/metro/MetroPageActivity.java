package com.example.smartcitytestv1.metro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.metro.adapter.PageAdapter;
import com.example.smartcitytestv1.metro.beans.MetroPage;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetroPageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView imageView;
    private List<MetroPage> metroPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_page);

        recyclerView = (RecyclerView) findViewById(R.id.metro_page_recycler);
        imageView = (ImageView) findViewById(R.id.metro_page_img);

        getLines();
        getImg();

    }

    public void getLines(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/metro/line/list");
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                metroPages = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject1 = data.getJSONObject(i);
                    int lineId = jsonObject1.getInt("lineId");
                    String lineName = jsonObject1.getString("lineName");
                    Log.d("TAG", "getLines: "+lineName);
                    metroPages.add(new MetroPage(lineId,lineName));
                }
                runOnUiThread(()->{
                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerView.setAdapter(new PageAdapter(R.layout.metro_page_layout,metroPages));
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void getImg(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/metro/city");
                JSONObject jsonObject = new JSONObject(result);
                String url = jsonObject.getJSONObject("data").getString("imgUrl");
                runOnUiThread(()->{
                    Glide.with(getBaseContext()).load("http://124.93.196.45:10001"+url).into(imageView);
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }


}