package com.example.hongaer.shoppingmall2.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hongaer on 2017/8/10.
 */

public class Http {


    public static String json;
    //public static String url=Constans.ADRRES_URL;

    static OkHttpClient okHttpClient = new OkHttpClient();
    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }
   /* public static void  get() {

        Log.e(TAG, "主页数据被初始化了");

        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG,"首页请求失败==="+e.getMessage());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                      json=response.toString();
                        //Log.i("tag11","请求数据成功======="+json);
                        //processData(response);
                    }

                });

      *//*   return  json;*//*
    }*/

}
