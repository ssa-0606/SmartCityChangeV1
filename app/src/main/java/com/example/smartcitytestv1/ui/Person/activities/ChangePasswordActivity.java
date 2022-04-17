package com.example.smartcitytestv1.ui.Person.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartcitytestv1.MainActivity;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.tools.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChangePasswordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText oldEdit;
    private EditText newEdit;
    private Button btn;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        setContentView(R.layout.activity_change_password);

        sharedPreferences = getSharedPreferences("data",0);
        editor = sharedPreferences.edit();

        oldEdit = (EditText) findViewById(R.id.old_pass);
        newEdit = (EditText) findViewById(R.id.new_pass);
        btn = (Button) findViewById(R.id.change_btn);

        toolbar = (Toolbar) findViewById(R.id.change_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        btn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(oldEdit.getText().toString().trim())||TextUtils.isEmpty(newEdit.getText().toString().trim())){
                Toast.makeText(this, "输入不得为空", Toast.LENGTH_SHORT).show();
            }else{
                String oldPass = oldEdit.getText().toString().trim();
                String newPass = newEdit.getText().toString().trim();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("oldPassword",oldPass);
                    jsonObject.put("newPassword",newPass);
                    change(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void change(String data){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.PUT_T("http://124.93.196.45:10001/prod-api/api/common/user/resetPwd",sharedPreferences.getString("token","k"),data);
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                String msg = jsonObject.getString("msg");
                if(code ==200){
                    runOnUiThread(()->{
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                        //修改密码成功后，跳转登录页，重新登录
                        Intent intent = new Intent(this, LogInActivity.class);
                        startActivity(intent);
                    });
                }else if(code == 500){
                    //旧密码输入错误
                    Log.d("TAG111", "change: "+msg);
                    runOnUiThread(()->{
                        Toast.makeText(this, "提示"+msg, Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

}