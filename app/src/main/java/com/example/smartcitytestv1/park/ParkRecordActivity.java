package com.example.smartcitytestv1.park;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.park.adapter.ParkRecordAdapter;
import com.example.smartcitytestv1.park.beans.ParkRecord;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ParkRecordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ParkRecord> parkRecords;
    private List<ParkRecord> showParkRecords;
    private Button showMore;

    private EditText entry;
    private EditText out;
    private Button search;

    private TextView tip;

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_record);

        recyclerView = (RecyclerView) findViewById(R.id.park_record_recycler);
        showMore = (Button) findViewById(R.id.park_record_btn);

        entry = (EditText) findViewById(R.id.park_input_entry);
        out = (EditText) findViewById(R.id.park_input_out);
        search = (Button) findViewById(R.id.park_record_search);

        tip = (TextView) findViewById(R.id.park_record_tip);

        toolbar = (Toolbar) findViewById(R.id.tool_Bar_park_record);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getRecord();

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(new ParkRecordAdapter(R.layout.park_record_layout,parkRecords));
                showMore.setVisibility(View.GONE);
            }
        });

        search.setOnClickListener(view -> {
            String entryText = String.valueOf(entry.getText());
            String outText = String.valueOf(out.getText());

            Boolean yanen = yanZheng(entryText);
            Boolean yanou = yanZheng(outText);

            boolean enKong = TextUtils.equals(entryText, "");
            boolean ouKong = TextUtils.equals(outText, "");

            //调试信息
            Log.d("TAG555", "onCreate: "+yanen+"|"+yanou);
            Log.d("TAG555", "|||"+TextUtils.equals(outText,"")+"|||"+TextUtils.equals(entryText,""));

            // TODO: 2022/4/16 可以试一试换个写法，因为执行的代码都一样，写法不好
            if(yanen&&yanou){
                //当都入场时间和出场时间都输入，并且都格式正确
                getRecordSearch(entryText,outText);
            }else if(yanen&& !yanou && ouKong){
                //当输入入场时间，但没输入出场时间，入场时间格式正确
                getRecordSearch(entryText,outText);
            }else if(!yanen&&yanou && enKong){
                //当输入出场时间，但没输入入场时间，出场时间格式正确
                getRecordSearch(entryText,outText);
            }else{
                Toast.makeText(this, "您输入的格式不正确", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public Boolean yanZheng(String data){
        //正则表达式验证日期格式
        boolean matches = Pattern.matches("^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", data);
        return matches;
    }


    public void getRecordSearch(String entry,String out){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/park/lot/record/list?entryTime="+entry+"&outTime="+out);
                JSONObject resultJson = new JSONObject(result);
                JSONArray rows = resultJson.getJSONArray("rows");
                parkRecords = new ArrayList<>();
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject data = rows.getJSONObject(i);
                    int id = data.getInt("id");
                    String plateNumber = data.getString("plateNumber");
                    String monetary = data.getString("monetary");
                    String parkName = data.getString("parkName");
                    String entryTime = data.getString("entryTime");
                    String outTime = data.getString("outTime");
                    parkRecords.add(new ParkRecord(id,plateNumber,monetary,parkName,entryTime,outTime));
                }
                handler.sendEmptyMessage(2);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void getRecord(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/park/lot/record/list");
                JSONObject resultJson = new JSONObject(result);
                JSONArray rows = resultJson.getJSONArray("rows");
                parkRecords = new ArrayList<>();
                for (int i = 0; i < rows.length(); i++) {
                    JSONObject data = rows.getJSONObject(i);
                    int id = data.getInt("id");
                    String plateNumber = data.getString("plateNumber");
                    String monetary = data.getString("monetary");
                    String parkName = data.getString("parkName");
                    String entryTime = data.getString("entryTime");
                    String outTime = data.getString("outTime");
                    parkRecords.add(new ParkRecord(id,plateNumber,monetary,parkName,entryTime,outTime));
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
                    showParkRecords = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        showParkRecords.add(parkRecords.get(i));
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerView.setAdapter(new ParkRecordAdapter(R.layout.park_record_layout,showParkRecords));
                    break;
                case 2:
                    if(parkRecords.size()==0){
                        TextView textView = new TextView(getBaseContext());
                        tip.setText("当前无数据");
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerView.setAdapter(new ParkRecordAdapter(R.layout.park_record_layout,parkRecords));
                    break;
            }
        }
    };


}