package com.example.smartcitytestv1.ui.Person.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcitytestv1.MainActivity;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LogInActivity extends AppCompatActivity {

    private EditText username ;
    private EditText password;
    private Button btn;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences = getSharedPreferences("data",0);
        editor = sharedPreferences.edit();

        username = (EditText) findViewById(R.id.user_log_in_name);
        password = (EditText) findViewById(R.id.user_log_in_password);
        btn = (Button) findViewById(R.id.user_log_in_btn);

        Log.d("TAG", "onCreate: "+username.getText());
        btn.setOnClickListener(view -> {
            logIn();
        });
    }

    public void logIn(){
        String userName = username.getText().toString().trim();
        String passWord = password.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passWord)){
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Thread thread = new Thread(()->{
            JSONObject json = new JSONObject();
            try {
                json.put("username",userName);          //Mikasa
                json.put("password",passWord);          //123890
                String tokenJson = MyUtils.POST("http://124.93.196.45:10001/prod-api/api/login", json.toString());
                JSONObject jsonObject = new JSONObject(tokenJson);
                int code = jsonObject.getInt("code");
                if(code == 200){
                    String token = jsonObject.getString("token");
                    runOnUiThread(()->{
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        editor.putString("username",userName);
                        editor.putString("password",passWord);
                        editor.putString("token",token);
                        editor.commit();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    });
                }else{
                    runOnUiThread(()->{
                        Toast.makeText(this, "登录失败，请重新登录", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    });
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

        });
        thread.start();
    }

}