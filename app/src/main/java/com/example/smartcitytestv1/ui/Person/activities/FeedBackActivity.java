package com.example.smartcitytestv1.ui.Person.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcitytestv1.R;

import java.util.Timer;
import java.util.TimerTask;

public class FeedBackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText feed;
    private TextView count;
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
        setContentView(R.layout.activity_feed_back);

        feed = (EditText) findViewById(R.id.feedback_text);
        count = (TextView) findViewById(R.id.feedback_count);
        btn = (Button) findViewById(R.id.feedback_btn);

        toolbar = (Toolbar) findViewById(R.id.feedback_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        feed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>150){
                    feed.setText(TextUtils.substring(charSequence,0,149));
                    feed.setSelection(150);
                    Toast.makeText(FeedBackActivity.this, "输入已满", Toast.LENGTH_SHORT).show();
                }
                count.setText("已经输入："+String.valueOf(charSequence.length()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn.setOnClickListener(view -> {
            feed.setText("");
            Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
        });

    }
}