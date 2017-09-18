package com.example.hongaer.shoppingmall2.shoppingcart.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.home.bean.DatasBean;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.adapter.ConfirmAdapter;
import com.example.hongaer.shoppingmall2.shoppingcart.utils.CartStorage;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.user.view.address.AddressListActivity;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConfirmOrderActivity extends Activity {

    private static final int ORDER_FLAG = 1;
    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.tv_receive)
    TextView tvReceive;
    @BindView(R.id.tv_raddress)
    TextView tvRaddress;
    @BindView(R.id.ib_goods_right)
    ImageButton ibGoodsRight;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    /*@BindView(R.id.order_listv)
    ListView orderListv;*/
    private SPConsigneeAddressBean consignee;
    private GoodsBean goodsBean;
    private List<DatasBean.DataBean> bean;
    private ConfirmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initData();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case ORDER_FLAG:
                    showData();
            }
        }
    };

    private void showData() {
        List<GoodsBean> goodsBeanList = CartStorage.getInstance().getAllData();

        Log.i("78787878", "主页数据被初始化了===" + goodsBeanList);
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            //有数据隐藏
            adapter = new ConfirmAdapter(this, goodsBeanList);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        }

    }


    private void initData() {

        if (getIntent() != null && getIntent().getSerializableExtra("consignee") != null) {

            consignee = (SPConsigneeAddressBean) getIntent().getSerializableExtra("consignee");
            //Log.e("hong666","66666666666======"+consignee);
            tvReceive.setText(consignee.getConsignee() + "    " + consignee.getMobile());
            tvRaddress.setText(consignee.getFullAddress() + consignee.getAddress());
        }
        String reg_url = Constans.SHOPPINGCART_URL;
        // final String token="90498ceb48917654d7116c2a9b8198fb";
        final String token = CacheUtils.getString(MyApplication.getContex(), "token");
        Log.e("6666888", "请求数据成功=======" + token);
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody body = new FormBody.Builder().add("token", token).build();
        Request request = new Request.Builder().url(reg_url).post(body).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.i("tag22233333", "请求数据成功=======" + json);
                    // Log.i("tag22233333", "请求数据成功=======" + token);
                    // processData(json);
                    //List<GoodsBean> goodsBeanList = processData(json);
                    processData(json);
                    mHandler.sendEmptyMessage(ORDER_FLAG);
                    /*Message msg=new Message();
                    msg.what=JSON_FLAG;
                    msg.obj=goodsBeanList;*/


                }
            }
        });

    }

    private void processData(String json) {
        DatasBean dataBean = JSON.parseObject(json, DatasBean.class);
        bean = dataBean.getData();

        if (bean != null && bean.size() > 0) {
            for (int i = 0; i < bean.size(); i++) {
                DatasBean.DataBean datasbean = bean.get(i);
                goodsBean = new GoodsBean();
                goodsBean.setName(datasbean.getName());
                goodsBean.setSellername(datasbean.getSellername());
                goodsBean.setCover_price(datasbean.getSellprice());
                goodsBean.setFigure(datasbean.getImg());
                goodsBean.setGoods_id(datasbean.getId());
                goodsBean.setType(datasbean.getType());
                Log.i("tag666666", "请求数据成功=======" + goodsBean);
                CartStorage.getInstance().addData(goodsBean);

                //goodsBeanList.add(goodsBean);
                //CartStorage.getInstance().addData(goodsBean);
                // Log.i("token222666","请求数据成功======="+goodsBeanList);
            }
            //Log.i("token22233333","请求数据成功======="+goodsBeanList);
        }
        // return  goodsBeanList;
    }


    @OnClick({R.id.ib_goods_back, R.id.tv_receive, R.id.tv_raddress, R.id.ib_goods_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.tv_receive:
                break;
            case R.id.tv_raddress:
                break;
            case R.id.ib_goods_right:
                Intent intent = new Intent(this, AddressListActivity.class);
                intent.putExtra("goods", "goods");
                startActivity(intent);
                break;
        }
    }
}
