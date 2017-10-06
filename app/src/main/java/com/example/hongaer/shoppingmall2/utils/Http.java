package com.example.hongaer.shoppingmall2.utils;

import com.tencent.open.utils.HttpUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hongaer on 2017/8/10.
 */

public class Http {
    private static OkHttpClient client = null;

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (HttpUtils.class) {
                if (client == null)
                    client = new OkHttpClient();
            }
        }
        return client;
    }

    static OkHttpClient okHttpClient = new OkHttpClient();
    public static String reg_url;

    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String post4(String id, String goods, String num, String token, String url) throws IOException {
        FormBody body = new FormBody.Builder().add("cartinfo", id).add("payment", goods).add("accept_time", num).add("token", token).build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
     public static String post3(String id, String goods,  String token, String url) throws IOException {
        FormBody body = new FormBody.Builder().add("goods_id",id).add("type", goods).add("token", token).build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
     public static String payPost(String id,String token, String url) throws IOException {
        FormBody body = new FormBody.Builder().add("orders",id).add("token", token).build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
    public static void doPost(String url, Map<String, String> mapParams, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
       Call call =getInstance().newCall(request);
        call.enqueue(callback);
    }

}