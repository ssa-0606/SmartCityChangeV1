package com.example.smartcitytestv1.park;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ParkDetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView parkName;
    private TextView parkAddress;
    private TextView parkDistance;
    private TextView open_test;
    private TextView allParks;
    private TextView vacancies;
    private TextView ratesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_detail);
        imageView = (ImageView) findViewById(R.id.park_detail_img);
        parkName = (TextView) findViewById(R.id.park_detail_name);
        parkAddress = (TextView) findViewById(R.id.park_detail_address);
        parkDistance = (TextView) findViewById(R.id.park_detail_distance);
        open_test = (TextView) findViewById(R.id.park_detail_open);
        allParks = (TextView) findViewById(R.id.park_detail_total);
        vacancies = (TextView) findViewById(R.id.park_detail_vacancy);
        ratesText = (TextView) findViewById(R.id.park_detail_rates);


        Intent intent = getIntent();
        int park_id = intent.getIntExtra("park_id",0);
//        Toast.makeText(this, park_id, Toast.LENGTH_SHORT).show();
//        Log.d("TAG2", "onCreate: "+park_id);
        getParkById(park_id);


    }


    public void getParkById(int id){
        Thread thread = new Thread(()->{

            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/park/lot/"+id);
                JSONObject jsonObject = new JSONObject(result);

                JSONObject data = jsonObject.getJSONObject("data");
                int id1 = data.getInt("id");
                String name = data.getString("parkName");
                String vacancy = data.getString("vacancy");
                String priceCaps = data.getString("priceCaps");
                String imgUrl = data.getString("imgUrl");
                String rates = data.getString("rates");
                String address = data.getString("address");
                String distance = data.getString("distance");
                String allPark = data.getString("allPark");
                String open = data.getString("open");

                runOnUiThread(()->{
                    Glide.with(getBaseContext()).load("http://124.93.196.45:10001/"+imgUrl).into(imageView);
                    parkName.setText(name);
                    parkAddress.setText(address);
                    parkDistance.setText("距您"+distance+"km");
                    allParks.setText(allPark);
                    vacancies.setText(vacancy);
                    ratesText.setText("收费标准："+rates+"元/小时 (最高 "+priceCaps+"元/小时)");

                    if(TextUtils.equals(open,"Y")){
                        open_test.setText("对外开放");
                        open_test.setTextColor(android.graphics.Color.parseColor("#00ff00"));
                    }else{
                        open_test.setText("未对外开放");
                        open_test.setTextColor(android.graphics.Color.parseColor("#ff0000"));
                    }

                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }


        });
        thread.start();
    }

}