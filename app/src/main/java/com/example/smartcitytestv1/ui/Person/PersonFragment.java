package com.example.smartcitytestv1.ui.Person;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smartcitytestv1.MainActivity;
import com.example.smartcitytestv1.R;
import com.example.smartcitytestv1.tools.MyUtils;
import com.example.smartcitytestv1.ui.Person.activities.ChangePasswordActivity;
import com.example.smartcitytestv1.ui.Person.activities.FeedBackActivity;
import com.example.smartcitytestv1.ui.Person.activities.LogInActivity;
import com.example.smartcitytestv1.ui.Person.activities.UserInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

public class PersonFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private LinearLayout layout;

    private TextView showLog;
    private TextView idText;
    private ImageView avatarImg;

    private Button logOut;

    private LinearLayout userInfo;
    private LinearLayout changePass;
    private LinearLayout feedBack;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.person_fragment, container, false);

        showLog = (TextView) view.findViewById(R.id.user_show_log);
        idText = (TextView) view.findViewById(R.id.user_id);
        avatarImg = (ImageView) view.findViewById(R.id.user_avatar);

        layout = (LinearLayout) view.findViewById(R.id.user_log_in);

        logOut = (Button) view.findViewById(R.id.user_lag_out);

        userInfo = (LinearLayout) view.findViewById(R.id.user_info);
        changePass = (LinearLayout) view.findViewById(R.id.user_change_password);
        feedBack = (LinearLayout) view.findViewById(R.id.user_feedback);

        sharedPreferences = getActivity().getSharedPreferences("data",0);
        editor = sharedPreferences.edit();
        //?????????????????????token
        verifyUser(sharedPreferences.getString("token","k"));
        //??????????????????????????????????????????????????????
        layout.setOnClickListener(view1 -> {
            if(TextUtils.equals(showLog.getText(),"????????????")){
                Intent intent = new Intent(getActivity(), LogInActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(), "???????????????", Toast.LENGTH_SHORT).show();
            }
        });
        //?????????????????????????????????
        logOut.setOnClickListener(view1 -> {
            editor.clear();
            editor.commit();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });

        //?????????????????????
        userInfo.setOnClickListener(view1 -> {
            if(TextUtils.equals(showLog.getText(),"????????????")){
                Toast.makeText(getContext(), "????????????", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
        //?????????????????????
        changePass.setOnClickListener(view1 -> {
            if(TextUtils.equals(showLog.getText(),"????????????")){
                Toast.makeText(getContext(), "????????????", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        //????????????????????????
        feedBack.setOnClickListener(view1 -> {
            if(TextUtils.equals(showLog.getText(),"????????????")){
                Toast.makeText(getContext(), "????????????", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(getActivity(), FeedBackActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void verifyUser(String token){
        Thread thread = new Thread(()->{
            try {
                String result = MyUtils.GET_T("http://124.93.196.45:10001/prod-api/api/common/user/getInfo", token);
                JSONObject jsonObject = new JSONObject(result);
                int code = jsonObject.getInt("code");
                //??????200?????? ?????????token ????????? ?????? ????????????????????????????????????????????????????????????????????????
                if(code != 200 ){
                    getActivity().runOnUiThread(() -> {
                        showLog.setText("????????????");
                    });
                }else {
                    //??????token???????????????????????????????????????????????????????????????
                    JSONObject user = jsonObject.getJSONObject("user");
                    String userId = user.getString("userId");
                    String nickName = user.getString("nickName");
                    String avatarUrl = user.getString("avatar");
                    //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    getActivity().runOnUiThread(()->{
                        showLog.setText(nickName);
                        idText.setText("????????????"+userId);
                        Glide.with(getContext()).load(avatarUrl).into(avatarImg);
                    });
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        });
        thread.start();
    }


}