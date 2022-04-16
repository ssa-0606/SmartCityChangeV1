package com.example.smartcitytestv1.park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.park.adapter.ParkAdapter;
import com.example.smartcitytestv1.park.beans.Park;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ParkActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView park_recycler;
    private List<Park> parkList;
    private List<Park> showParkList;
    private Button show_more;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return true;
    }

    //活动栏 的 事件监听 包括回退上一页
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.park_record_item:
                //跳转停车记录页
//                Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ParkRecordActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);


        toolbar = (Toolbar) findViewById(R.id.tool_Bar_park);
        park_recycler = (RecyclerView) findViewById(R.id.park_list);
        show_more = (Button) findViewById(R.id.show_more);

        //添加回退按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//            另一种回退按钮事件
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


//              另一种实现 跳转实现
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                switch (item.getItemId()){
//                    case android.R.id.home:
//                        finish();
//                        break;
//                    case R.id.park_record_item:
//                        Toast.makeText(ParkActivity.this, "345", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//
//                return true;
//            }
//        });




        getPark();

        show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showParkList = new ArrayList<>();
                for (int i = 0; i < parkList.size(); i++) {
                    showParkList.add(parkList.get(i));
                }
                park_recycler.setAdapter(new ParkAdapter(R.layout.park_layout,showParkList));
                show_more.setVisibility(View.GONE);
            }
        });

    }

    public void getPark(){

        Thread thread = new Thread(()->{
            try {
                String resultJson = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/park/lot/list");
                JSONObject result = new JSONObject(resultJson);
                JSONArray rows = result.getJSONArray("rows");
                parkList = new ArrayList<>();
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject data = rows.getJSONObject(i);
                    int id = data.getInt("id");
                    String parkName = data.getString("parkName");
                    String vacancy = data.getString("vacancy");
                    String address = data.getString("address");
                    String priceCaps = data.getString("priceCaps");
                    String imgUrl = data.getString("imgUrl");
                    String rates = data.getString("rates");
                    String distance = data.getString("distance");
                    String allPark = data.getString("allPark");
                    String open = data.getString("open");
//                    Log.d("TAG1", "getPark: "+new Park(id,parkName,vacancy,address,priceCaps,imgUrl,rates,distance,allPark,open));
                    parkList.add(new Park(id,parkName,vacancy,address,priceCaps,imgUrl,rates,distance,allPark,open));
                }
                parkList.sort((t1,t2)->{
                    if(Integer.parseInt(t1.getDistance())>Integer.parseInt(t2.getDistance())) return 1;
                    else return -1;
                });
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
                    showParkList = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        showParkList.add(parkList.get(i));
                    }
                    park_recycler.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    park_recycler.setAdapter(new ParkAdapter(R.layout.park_layout,showParkList));
                    break;
            }
        }
    };
}