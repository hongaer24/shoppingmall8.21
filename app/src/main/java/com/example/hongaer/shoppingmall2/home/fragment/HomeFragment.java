package com.example.hongaer.shoppingmall2.home.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.base.BaseFragment;
import com.example.hongaer.shoppingmall2.home.adapter.HomeFragmentAdapter;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.home.bean.ResultDataBean;
import com.example.hongaer.shoppingmall2.user.view.PositionActivity;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.example.hongaer.shoppingmall2.utils.Http;

import java.io.IOException;

import okhttp3.OkHttpClient;

/**
 * Created by hongaer on 2017/7/1.
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final String GOODS_BEAN = "goodsBean";
    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;
    private ResultDataBean.ResultBean resultbean;
    private HomeFragmentAdapter adapter;
    private WebView webview;
    //public String url = Constans.HOME_URLS;
    public String url = Constans.HOME_URLS;
    private CharSequence response;
    OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    public View initView()  {
        Log.e(TAG, "主页视图被初始化了");
        Log.i(TAG, "主页视图被初始化了66666");


        View view = View.inflate(mContext, R.layout.fragment_home,null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        webview= (WebView) view.findViewById(R.id.wb);
        WebSettings webSettings=webview.getSettings();
         webSettings.setUseWideViewPort(true);
         webSettings.setJavaScriptEnabled(true);
         webview.setWebViewClient(new WebViewClient());
         webview.loadUrl( "http://killsound888.oicp.net:118/jinque/debug/jqmall/index.php?controller=site&action=index");
         webview.setWebViewClient(new HelloWebViewClient());
        //Web视图

       /* rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);*/
//设置点击事件
        /*initListener();*/
        return view;
    }
   /* public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json=Http.get(url);
                    Log.i("tag11ww", "请求数据成功======="+json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }
*/
    private class HelloWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
   /* String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }*/
/*

      public  void Data(){

        try {
            http(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/


    public void initData()  {
        super.initData();
        Log.e(TAG, "http主页数据被初始化了");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json= null;
                try {
                    json = Http.get(url);
                    processData(json);
                   // Log.e(TAG,"解析成功===="+ resultbean);
                } catch (IOException e) {

                }

               Log.i("tag11","请求数据成功======="+json);


            }
        }).start();


        }





   /* public String http(String url) throws IOException {

        final Request request = new Request.Builder().url(url).build();
              new Thread(new Runnable() {
                  @Override
                  public void run() {

                      Response response = null;
                      try {
                          response = okHttpClient.newCall(request).execute();
                          if (response.isSuccessful()) {

                               Log.i("tag11２２", "请求数据成功=======" + json);

                          }else {
                              throw new IOException("Unexpected code " + response);
                          }

                      } catch (IOException e) {
                          e.printStackTrace();
                      }

                  }
              }).start();
        return response.body().string();

    }*/
       /* OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "首页请求失败===" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                      json = response.toString();


                        //processData(response);
                    }

                });*/
    // return json = response.toString();


     /*   return url;*/

    private void processData(String json) {
      ResultDataBean  resultDataBean= JSON.parseObject(json,ResultDataBean.class);
            // Log.e(TAG,"解析成功====="+datasBean);
            resultbean= resultDataBean.getResult();
            /* DatasBean.DataBean dataBean =JSON.parseObject(json, DatasBean.DataBean.class);
                  Log.i("databean","========"+dataBean);*/
       // Log.e(TAG,"解析成功===="+ resultbean.getBanner_info().get(1).getImage());
        if (resultbean != null) {
            adapter = new HomeFragmentAdapter(mContext, resultbean);
            GoodsBean goodsBean=new GoodsBean();
            ResultDataBean.ResultBean.RecommendInfoBean  recommendInfoBean= (ResultDataBean.ResultBean.RecommendInfoBean) resultbean.getRecommend_info().get(1);
            goodsBean.setName( recommendInfoBean.getName());
            goodsBean.setCover_price( recommendInfoBean.getCover_price());
            goodsBean.setFigure( recommendInfoBean.getFigure());
            goodsBean.setProduct_id( recommendInfoBean.getProduct_id());
            startGoodsInfoActivity(goodsBean);
           // rvHome.setAdapter(adapter);
            //rvHome.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            //GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
                 //设置滑动到哪个位置了的监听
          /*  manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position <= 3) {
                        ib_top.setVisibility(View.GONE);
                    } else {
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    //只能返回 1
                    return 1;
                }
            });*/
                      //设置网格布局
           // rvHome.setLayoutManager(manager);
         }

       //  else{}

    }
    private void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent=new Intent(mContext, PositionActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }


 /*   private void initListener() {
//置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//回到顶部
                rvHome.scrollToPosition(0);
            }
        });
//搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索",
                        Toast.LENGTH_SHORT).show();
            }
        });
//消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}