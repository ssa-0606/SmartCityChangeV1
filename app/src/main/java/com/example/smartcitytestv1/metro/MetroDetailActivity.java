package com.example.smartcitytestv1.metro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.metro.adapter.LineAdapter;
import com.example.smartcitytestv1.metro.beans.Line;
import com.example.smartcitytestv1.metro.beans.MetroStationLine;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetroDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MetroStationLine metroStationLine;
    private List<Line> lines;

    private TextView toolText;
    private TextView firstText;
    private TextView endText;
    private TextView reachText;
    private TextView stationsText;
    private TextView kmText;

    private RecyclerView recyclerView;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_detail);

        toolText = (TextView) findViewById(R.id.metro_detail_line_name);
        firstText = (TextView) findViewById(R.id.metro_first);
        endText = (TextView) findViewById(R.id.metro_end);
        reachText = (TextView) findViewById(R.id.metro_reach_time);
        stationsText = (TextView) findViewById(R.id.metro_station_num);
        kmText = (TextView) findViewById(R.id.metro_distance);

        recyclerView = (RecyclerView) findViewById(R.id.metro_line_recycler);


        toolbar = (Toolbar) findViewById(R.id.metro_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        int lineId = intent.getIntExtra("lineId", 31);
        Log.d("TAG1", "onCreate: "+lineId);

        getInfo(lineId);

    }


    public void getInfo(int lineId){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/metro/line/"+lineId);
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject("data");
                int id = data.getInt("id");
                String name = data.getString("name");
                String first = data.getString("first");
                String end = data.getString("end");
                int stationsNumber = data.getInt("stationsNumber");
                int km = data.getInt("km");
                String runStationsName = data.getString("runStationsName");
                int remainingTime = data.getInt("remainingTime");
                JSONArray metroStepList = data.getJSONArray("metroStepList");
                lines = new ArrayList<>();
                for (int i = 0; i < metroStepList.length(); i++) {
                    JSONObject jsonObject1 = metroStepList.getJSONObject(i);
                    int id1 = jsonObject1.getInt("id");
                    String name1 = jsonObject1.getString("name");
                    lines.add(new Line(id1,name1));
                }
                metroStationLine = new MetroStationLine(id,name,first,end,stationsNumber,km,runStationsName,remainingTime,lines);
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
                    toolText.setText(metroStationLine.getName());
                    firstText.setText(metroStationLine.getFirst());
                    endText.setText(metroStationLine.getEnd());
                    reachText.setText(metroStationLine.getRemainingTime()+"分钟");
                    stationsText.setText(metroStationLine.getStationsNumber()+"站");
                    kmText.setText(metroStationLine.getKm()+"km");
                    Log.d("TAG", "handleMessage: "+getBaseContext()+"|"+recyclerView);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                    layoutManager.setOrientation(RecyclerView.HORIZONTAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new LineAdapter(R.layout.lines_layout,metroStationLine.getLines(),metroStationLine.getRunStationsName()));
                    break;
            }
        }
    };


}