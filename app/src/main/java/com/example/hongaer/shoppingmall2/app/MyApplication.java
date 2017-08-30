package com.example.hongaer.shoppingmall2.app;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by hongaer on 2017/7/3.
 */

public class MyApplication extends Application {
    public static Context mContex;

    public static Context getContex() {
        return mContex;
    }

    public void onCreate()
    {
        super.onCreate();
          this.mContex=this;
        /*
           初始化okhttputils
        */

        initOkhttpClient();
       // registToWX();

    }
   /* private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, AppConst.WEIXIN.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(AppConst.WEIXIN.APP_ID);
    }*/

    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

}
