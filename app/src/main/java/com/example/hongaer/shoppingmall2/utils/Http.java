package com.example.hongaer.shoppingmall2.utils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hongaer on 2017/8/10.
 */

public class Http {


    // public static String json;
    //public static String url=Constans.ADRRES_URL;

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
        FormBody body = new FormBody.Builder().add("goods_id", id).add("type", goods).add("goods_num", num).add("token", token).build();
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
}