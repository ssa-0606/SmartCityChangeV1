package com.example.smartcitytestv1.ui.Person.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.MainActivity;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserInfoActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Toolbar toolbar;

    private ImageView imageView;
    private EditText nickText;
    private RadioGroup radioGroup;
    private RadioButton man;
    private RadioButton woman;
    private EditText phoneNum;

    private Button btn;

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
        setContentView(R.layout.activity_user_info);
        toolbar = findViewById(R.id.user_info_toolbar);

        sharedPreferences = getSharedPreferences("data",0);
        editor = sharedPreferences.edit();

        imageView = (ImageView) findViewById(R.id.user_info_avatar);
        nickText = (EditText) findViewById(R.id.user_info_nick);
        radioGroup = (RadioGroup) findViewById(R.id.user_info_sex);
        man = (RadioButton) findViewById(R.id.user_info_sex_male);
        woman = (RadioButton) findViewById(R.id.user_info_sex_female);
        phoneNum = (EditText) findViewById(R.id.user_info_phone);

        btn = (Button) findViewById(R.id.user_info_btn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getInfo();

        btn.setOnClickListener(view -> {

            if(TextUtils.isEmpty(nickText.getText().toString().trim())||TextUtils.isEmpty(phoneNum.getText().toString().trim())){
                Toast.makeText(this, "输入不得为空", Toast.LENGTH_SHORT).show();
            }else{
                String nick = nickText.getText().toString();
                String phone = phoneNum.getText().toString();
                String sex = "0";
                if(man.isChecked()){
                    sex="0";
                }else{
                    sex="1";
                }

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("nickName", nick);
                    jsonObject.put("phonenumber", phone);
                    jsonObject.put("sex",sex);
                    submit(jsonObject.toString());
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }



        });


    }

    public void getInfo(){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET_T("http://124.93.196.45:10001/prod-api/api/common/user/getInfo",sharedPreferences.getString("token","k"));
                JSONObject jsonObject = new JSONObject(result);
                JSONObject user = jsonObject.getJSONObject("user");
                String avatar = user.getString("avatar");
                String nickName = user.getString("nickName");
                String sex = user.getString("sex");
                String phonenumber = user.getString("phonenumber");
                String phone = TextUtils.substring(phonenumber,0,6)+"****";
                runOnUiThread(()->{
                    Glide.with(getBaseContext()).load(avatar).into(imageView);
                    nickText.setText(nickName);
                    if(TextUtils.equals(sex,"0")){
                        man.setChecked(true);
                    }else{
                        woman.setChecked(true);
                    }
                    phoneNum.setText(phone);
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void submit(String data) throws IOException, JSONException {
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.PUT_T("http://124.93.196.45:10001/prod-api/api/common/user",sharedPreferences.getString("token","k"),data);
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                if(code ==200){
                    runOnUiThread(()->{
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    });
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        });
        thread.start();
    }

}