package com.example.hongaer.shoppingmall2.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.baichuan.trade.common.adapter.ut.AlibcUserTracker;
import com.ut.mini.internal.UTTeamWork;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by hongaer on 2017/7/3.
 */

public class MyApplication extends Application {
    public static MyApplication application = null;
    public static Context mContex;

    public static Context getContex() {
        return mContex;
    }

    public void onCreate()
    {
        super.onCreate();
        application = this;
        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();
                //初始化成功，设置相关的全局配置参数
                Map utMap = new HashMap<>();
                utMap.put("debug_api_url","http://muvp.alibaba-inc.com/online/UploadRecords.do");
                utMap.put("debug_key","baichuan_sdk_utDetection");
                UTTeamWork.getInstance().turnOnRealTimeDebug(utMap);
               AlibcUserTracker.getInstance().sendInitHit4DAU("19","3.1.1.100");

            }

            @Override
            public void onFailure(int code, String msg) {
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
                Toast.makeText(MyApplication.this, "初始化失败,错误码="+code+" / 错误消息="+msg, Toast.LENGTH_SHORT).show();
                Log.i("初始化失败","======="+code);
                Log.i("初始化失败","======="+msg);
            }
        });
          this.mContex=this;
        /*
           初始化okhttputils
        */
        initOkhttpClient();
       //初始化jpush
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
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
    //设置jpush的debug模式打开

    //jpush的初始化


}
