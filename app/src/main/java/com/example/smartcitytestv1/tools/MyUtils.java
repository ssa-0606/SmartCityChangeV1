package com.example.smartcitytestv1.tools;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyUtils {

    public static String GET(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response execute = okHttpClient.newCall(request).execute();
        return execute.body().string();
    }

}
