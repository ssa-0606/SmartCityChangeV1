package com.example.smartcitytestv1.city_acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.city_acitivity.adapter.ActivityAdapter;
import com.example.smartcitytestv1.city_acitivity.adapter.ActivityCommentAdapter;
import com.example.smartcitytestv1.city_acitivity.beans.ActivityItem;
import com.example.smartcitytestv1.city_acitivity.beans.CommentUser;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private Toolbar toolbar;
    private TextView textView;
    private WebView textView1;

    private RecyclerView commentList;
    private List<CommentUser> commentUserList;

    //注意： 输入框 默认 自动获得 焦点 ，效果非常不好，可以在他的父级容器内添加   android:focusable="false"
    //                                                               android:focusableInTouchMode="true"
    private EditText comment;
    private Button comment_btn;

    private RecyclerView recyclerView;
    private List<ActivityItem> activityItems;

    private Button btn ;
    private boolean isSign;


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

        sharedPreferences = getSharedPreferences("data",0);
        textView = findViewById(R.id.activity_detail_title);
        textView1 = findViewById(R.id.activity_detail_content);
        commentList = findViewById(R.id.activity_comment_list);
        comment = findViewById(R.id.activity_comment);
        comment_btn = findViewById(R.id.activity_comment_btn);

        recyclerView = findViewById(R.id.activity_record);

        btn = findViewById(R.id.activity_sign_up);


        //webView 设置 图片 文字等， webView 大片空白更改
        WebSettings settings = textView1.getSettings();
        settings.setTextZoom(300);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        textView1.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                textView1.measure(w,h);
            }
        });

        //toolbar 返回按钮
        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        int id = getIntent().getIntExtra("id", 0);

        getDetail(id);
        getComments(id);
        comment_btn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(comment.getText().toString().trim())){
                Toast.makeText(this, "输入不得为空", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("activityId",id);
                    jsonObject.put("content",comment.getText().toString().trim());
                    submit(jsonObject.toString(),id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        getReComments();

        isSingUp(id);

        btn.setOnClickListener(view -> {
            if(!isSign){
                singUp(id);
            }
        });

    }

    private void singUp(int id) {

        Thread thread = new Thread(()->{
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("activityId",id);
                String result = MyUtils.POST_T("http://124.93.196.45:10001/prod-api/api/activity/signup", sharedPreferences.getString("token", "k"), jsonObject.toString());
                int code = new JSONObject(result).getInt("code");
                if(code ==200){
                    runOnUiThread(()->{
                        Toast.makeText(this, "报名成功", Toast.LENGTH_SHORT).show();
                        btn.setText("已报名");
                        btn.setClickable(false);
                    });
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void isSingUp(int id) {
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET_T("http://124.93.196.45:10001/prod-api/api/activity/signup/check?activityId=" + id,sharedPreferences.getString("token","k"));
                Log.d("TAG", "isSingUp: test");
                boolean isSignUp = new JSONObject(result).getBoolean("isSignup");
                if(isSignUp){
                    runOnUiThread(()->{
                        Log.d("TAG", "isSingUp: ");
                        btn.setText("已报名");
                        btn.setClickable(false);
                        isSign = true;
                    });
                }else{
                    runOnUiThread(()->{
                        isSign = false;
                        btn.setText("点击报名");
                    });
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    //提交评论的方法
    private void submit(String msg,int id) {
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.POST_T("http://124.93.196.45:10001/prod-api/api/activity/comment", sharedPreferences.getString("token", "k"), msg);
                int code = new JSONObject(result).getInt("code");
                if(code == 200){
                    runOnUiThread(()->{
                        Toast.makeText(this, "评论成功！！！", Toast.LENGTH_SHORT).show();
                        comment.setText("");
                        getComments(id);
                    });
                }else{
                    String msg1 = new JSONObject(result).getString("msg");
                    runOnUiThread(()->{
                        Toast.makeText(this, "评论失败："+msg1, Toast.LENGTH_SHORT).show();
                        comment.setText("");
                    });
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    //获取活动详情页信息方法
    private void getDetail(int id) {
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/activity/activity/"+id);
                JSONObject data = new JSONObject(result).getJSONObject("data");
                String name = data.getString("name");
                String content = data.getString("content");
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
    //获取评论列表的方法
    private void getComments(int id){
        Thread thread = new Thread(()->{

            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/activity/comment/list?activityId=" + id);
                commentUserList = new ArrayList<>();
                JSONArray rows = new JSONObject(result).getJSONArray("rows");
                for (int i = 0; i < 10; i++) {
                    JSONObject jsonObject = rows.getJSONObject(i);
                    String nickName = jsonObject.getString("nickName");
                    String content = jsonObject.getString("content");
                    String commentTime = jsonObject.getString("commentTime");
                    String avatar = jsonObject.getString("avatar");
                    commentUserList.add(new CommentUser(nickName,content,commentTime,avatar));
                }
                runOnUiThread(()->{
                    commentList.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    commentList.setAdapter(new ActivityCommentAdapter(R.layout.comment_layout,commentUserList));
                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    //获取推荐
    private void getReComments(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET("http://124.93.196.45:10001/prod-api/api/activity/activity/list?recommend=Y");
                activityItems = new ArrayList<>();
                JSONArray rows = new JSONObject(result).getJSONArray("rows");
                for (int j = 0; j < 3; j++) {
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
                runOnUiThread(()->{
                    recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
                    recyclerView.setAdapter(new ActivityAdapter(R.layout.my_activity_list,activityItems));
                });
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