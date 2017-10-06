package com.example.hongaer.shoppingmall2.shoppingcart.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.home.bean.DatasBean;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.shoppingcart.adapter.ConfirmAdapter;
import com.example.hongaer.shoppingmall2.shoppingcart.bean.StoreBean;
import com.example.hongaer.shoppingmall2.shoppingcart.pay.PayResult;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.user.view.address.AddressListActivity;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.example.hongaer.shoppingmall2.utils.Http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ConfirmOrderActivity extends Activity {

    private static final int SDK_PAY_FLAG = 1;
    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.tv_receive)
    TextView tvReceive;
    @BindView(R.id.tv_raddress)
    TextView tvRaddress;
    @BindView(R.id.ib_goods_right)
    ImageButton ibGoodsRight;
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.exListView)
    ExpandableListView exListView;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;

    private String orderInfo = "alipay_sdk=alipay-sdk-php-20161101&app_id=2017081208153694&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%2220170125test01%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=%E5%95%86%E6%88%B7%E5%A4%96%E7%BD%91%E5%8F%AF%E4%BB%A5%E8%AE%BF%E9%97%AE%E7%9A%84%E5%BC%82%E6%AD%A5%E5%9C%B0%E5%9D%80&sign_type=RSA2&timestamp=2017-09-24+09%3A50%3A49&version=1.0&sign=YyYdTdAXdG1T8PbcdKHgkaK%2BkcP2iS17M595Gy4%2FRTpVN1L7Jll5%2FR5uR0GNpvH%2F7Grws5SxWUH8rxqcdCpEIKIGT5%2BxCreOVwlKkVEWNJ5sOJjWfN54GnyXx4x8LGXkyP4plgZizay3IWARAGuMaxi9rSi4oUv8F%2B5T67qkxTz6NVlBbJkUDd3gnvnHmGV2L%2B0Jg0SUN3P8bVLLL%2BVyDzC1MWa7toFKvXx33L3MWy%2Ft214zyaR6uez3Ndog1N3HuVqXSagAYQcqym8fQ%2FHhDrjUn145s0y94mFtQ7p4Ky%2FntNCtwmao6CJqJymkI7Fo3uE%2FXGY5%2FVDzoS%2BG0niVHA%3D%3D";
    private SPConsigneeAddressBean consignee;
    private GoodsBean goodsBean;
    private List<DatasBean.DataBean> bean;
    private ConfirmAdapter adapter;
    private List<StoreBean> groups = new ArrayList<StoreBean>();
    private Map<String, List<GoodsBean>> children = new HashMap<String, List<GoodsBean>>();

    private  Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initData();
    }

    private void showData() {

        if (getIntent() != null && getIntent().getSerializableExtra("groups") != null&&getIntent() != null &&
                getIntent().getSerializableExtra("children") != null) {
            //有数据隐藏
             groups= (List<StoreBean>) getIntent().getSerializableExtra("groups");
             children= (Map<String, List<GoodsBean>>) getIntent().getSerializableExtra("children");
               Log.i("hong666","66666666666======"+groups);
            adapter = new ConfirmAdapter(this, groups, children, tvShopcartTotal);
            exListView.setAdapter(adapter);
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
            }
        }

    }
    public void pay() {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    private  void orderHttp(){
        String token = CacheUtils.getString(MyApplication.getContex(), "token");
        Log.i("99999", "请求数据成功======="+token);
        String url= Constans.ALIPAY_URL;
        Map<String,String> map=new HashMap<>();
       /* map.put("id","1");
        map.put("acceptname","小吴");
        map.put("province","海南");
        map.put("city","海南");
        map.put("area","美兰");
        map.put("tel","110");
        map.put("mobie","110");
        map.put("isdefault","1");*/
        map.put("orders","{\"1\",:\"20171006112158429837\"}");
        map.put("token",token);
        Http.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    String json=response.body().string();
                    Log.i("99999", "请求数据成功======="+json);
                }
            }
        });

    }

    private void initData() {

        if (getIntent() != null && getIntent().getSerializableExtra("consignee") != null) {

            consignee = (SPConsigneeAddressBean) getIntent().getSerializableExtra("consignee");
            //Log.e("hong666","66666666666======"+consignee);
            tvReceive.setText(consignee.getConsignee() + "    " + consignee.getMobile());
            tvRaddress.setText(consignee.getFullAddress() + consignee.getAddress());
        }
        showData();
       /* String reg_url = Constans.SHOPPINGCART_URL;
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
                    *//*Message msg=new Message();
                    msg.what=JSON_FLAG;
                    msg.obj=goodsBeanList;*//*


                }
            }
        });
*/
    }


    @OnClick({R.id.ib_goods_back, R.id.tv_receive, R.id.tv_raddress, R.id.ib_goods_right,R.id.btn_check_out})
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
            case R.id.btn_check_out:
                orderHttp();
                //pay();
                break;
        }
    }

}
