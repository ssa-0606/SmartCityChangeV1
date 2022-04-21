package com.example.smartcitytestv1.city_acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;
    private WebView textView1;

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
        setContentView(R.layout.activity_detail);

        textView = findViewById(R.id.activity_detail_title);
        textView1 = findViewById(R.id.activity_detail_content);

        WebSettings settings = textView1.getSettings();
        settings.setTextZoom(300);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);


        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        int id = getIntent().getIntExtra("id", 0);

        getDetail(id);

    }

    private void getDetail(int id) {
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/activity/activity/"+id);
                JSONObject data = new JSONObject(result).getJSONObject("data");
                String name = data.getString("name");
                String imgUrl = data.getString("imgUrl");
                String content = data.getString("content");
                Log.d("TAG", Html.fromHtml(content).toString());
                runOnUiThread(()->{
                    textView.setText(name);
                    textView1.loadDataWithBaseURL(null,content,"text/html","UTF-8",null);
                });
//                  使用HTMl.fromHtml     弃用    使用webview
//                getImg();


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

//    public void getImg(){
//        Thread thread = new Thread(()->{
//            Html.ImageGetter imageGetter = new Html.ImageGetter() {
//                @Override
//                public Drawable getDrawable(String s) {
//                    try {
//                        Drawable fromStream = Drawable.createFromStream(new URL(s).openStream(), null);
//                        fromStream.setBounds(0, 100,fromStream.getIntrinsicWidth(),fromStream.getIntrinsicHeight());
//                        return fromStream;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return null;
//                    }
//                }
//            };
//            CharSequence charSequence = Html.fromHtml(detailInfo.getContent(), Html.FROM_HTML_MODE_COMPACT,imageGetter, null);
//            Message message = new Message();
//            message.obj = charSequence;
//            message.what = 1;
//            handler.sendMessage(message);
//        });
//        thread.start();
//    }


//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case 1:
//                    textView.setText(detailInfo.getName());
////                    textView1.setText((CharSequence) msg.obj);
//                    break;
//            }
//        }
//    };


}