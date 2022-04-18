package com.example.smartcitytestv1.metro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;


import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.metro.adapter.MetroLineAdapter;
import com.example.smartcitytestv1.metro.beans.MetroLines;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetroActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<MetroLines> metroLines;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.park_record_item:
                Intent intent = new Intent(getBaseContext(),MetroPageActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro);

        recyclerView = (RecyclerView) findViewById(R.id.metro_recycler);

        toolbar = findViewById(R.id.metro_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        getMetroList();


    }

    public void getMetroList(){
        Thread thread = new Thread(()->{

            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/metro/list?currentName=建国门");
                metroLines = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject1 = data.getJSONObject(i);
                    int lineId = jsonObject1.getInt("lineId");
                    String lineName = jsonObject1.getString("lineName");
                    int reachTime = jsonObject1.getInt("reachTime");
                    String nextStep = jsonObject1.getJSONObject("nextStep").getString("name");
//                    Log.d("TAG-METRO", lineId+lineName+reachTime+nextStep);
                    metroLines.add(new MetroLines(lineId,lineName,reachTime,nextStep));
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
                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerView.setAdapter(new MetroLineAdapter(R.layout.metro_layout,metroLines));
                    break;
            }
        }
    };

}