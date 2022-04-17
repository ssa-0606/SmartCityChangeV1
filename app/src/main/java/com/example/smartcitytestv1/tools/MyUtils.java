package com.example.smartcitytestv1.tools;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyUtils {
    /*
    * 普通get方法，返回响应
    * */
    public static String GET(String url) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response execute = okHttpClient.newCall(request).execute();
        return execute.body().string();
    }

    /*
    * 普通post方法，接受请求体，返回响应
    * */
    public static String POST(String url,String msg) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,msg);
        Request request = new Request.Builder().url(url).method("POST",body).addHeader("Content-Type","application/json").build();
        return okHttpClient.newCall(request).execute().body().string();
    }

    public static String GET_T(String url,String token) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).method("GET",null).addHeader("Authorization",token).build();
        Response execute = okHttpClient.newCall(request).execute();
        return execute.body().string();
    }


    public static String PUT_T(String url,String token,String msg) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,msg);
        Request request = new Request.Builder().url(url).method("PUT",body).addHeader("Content-Type","application/json").addHeader("Authorization",token).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
