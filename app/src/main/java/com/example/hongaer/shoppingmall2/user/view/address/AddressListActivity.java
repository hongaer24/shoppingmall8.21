package com.example.hongaer.shoppingmall2.user.view.address;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.shoppingcart.view.ConfirmOrderActivity;
import com.example.hongaer.shoppingmall2.user.adapter.SPAddressListAdapter;
import com.example.hongaer.shoppingmall2.user.bean.AddressDatasBean;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.user.utils.AddressStorage;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.example.hongaer.shoppingmall2.utils.Http;
import com.example.hongaer.shoppingmall2.utils.SPDialogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.hongaer.shoppingmall2.shoppingcart.fragmet.ShoppingCartFragment.JSON_FLAG;

public class AddressListActivity extends AppCompatActivity implements SPAddressListAdapter.AddressListListener {

    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.address_listv)
    ListView addressListv;
    @BindView(R.id.address_list_pcl)
    PtrClassicFrameLayout addressListPcl;
    @BindView(R.id.add_address_btn)
    Button addAddressBtn;
    PtrClassicFrameLayout ptrClassicFrameLayout;
    //private List<GoodsBean> goodsBeanList;
    private List<SPConsigneeAddressBean> spConsigneeAddressBeanList;
    SPAddressListAdapter mAdapter;
    //SPConsigneeAddressBean selectConsignee;
    private DialogFragment mLoadingDialog;
    private List<AddressDatasBean.AddressBean> addressbean;
    private SPConsigneeAddressBean consignee;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case JSON_FLAG:
                    showData();
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        initData();

    }

    public void onResume() {
        super.onResume();
        initData();

    }

    public void initData() {
        String token = CacheUtils.getString(MyApplication.getContex(), "token");
        String url = Constans.ADDRESS_GET_URL;
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        Http.doPost(url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.i("666", "请求数据成功=======" + json);
                    processData(json);
                    mHandler.sendEmptyMessage(JSON_FLAG);
                    Message msg = new Message();
                    msg.what = JSON_FLAG;
                }
            }
        });

    }

    private void processData(String json) {
        AddressDatasBean addressDatasBean= JSON.parseObject(json,AddressDatasBean.class);
        addressbean=addressDatasBean.getAddress();
         if(addressbean!=null&&addressbean.size()>0){
            for(int i=0;i<addressbean.size();i++){
                AddressDatasBean.AddressBean addressBean=addressbean.get(i);
                consignee=new SPConsigneeAddressBean();
                consignee.setAddressID(addressBean.getId());
                consignee.setConsignee(addressBean.getAccept_name());
                consignee.setMobile(addressBean.getMobile());
                consignee.setAddress(addressBean.getAddress());
                consignee.setProvince(addressBean.getProvince());
                consignee.setCity(addressBean.getCity());
                consignee.setDistrict(addressBean.getArea());
                consignee.setIsDefault(addressBean.getIs_default());
                AddressStorage.getInstance().addData(consignee);

                Log.i("22233333", "请求数据成功=======" + consignee);

            }


         }


    }

    public void showData() {

        spConsigneeAddressBeanList= AddressStorage.getInstance().getAllData();
        mAdapter = new SPAddressListAdapter(this, this, spConsigneeAddressBeanList);
        addressListv.setAdapter(mAdapter);
        // ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {


           /* public void onRefreshBegin(PtrFrameLayout frame) {
                //下拉刷新
                refreshData();
            }
        });
        refreshData();*/
    }
    private void addressDeleteHttp(SPConsigneeAddressBean consignee){
        String token= CacheUtils.getString(MyApplication.getContex(), "token");
        String url=Constans.ADDRESS_DELETE_URL;
        Map<String,String> map=new HashMap<>();
        map.put("id",consignee.getAddressID());
        map.put("token",token);
        Log.i("22266","请求数据成功======="+ map);
        Http.doPost(url, map, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json=response.body().string();
                    Log.i("22266","请求数据成功======="+json);
                }

            }
        });



    }


    /*public void refreshData(){

            showLoadingToast();
            SPPersonRequest.getConsigneeAddressList(new SPSuccessListener() {
                @Override
                public void onRespone(String msg, Object response) {
                    if (response != null) {
                        consignees = (List<SPConsigneeAddressBean>) response;
                        dealModels();
                        mAdapter.setData(consignees);
                    }
                    ptrClassicFrameLayout.refreshComplete();
                    ptrClassicFrameLayout.setLoadMoreEnable(false);
                    hideLoadingToast();
                }
            }, new SPFailuredListener(SPConsigneeAddressListActivity.this) {
                @Override
                public void onRespone(String msg, int errorCode) {
                    hideLoadingToast();
                    showToast(msg);
                }
            });
        }
    */
    public void onItemEdit(SPConsigneeAddressBean consignee) {
        Intent intent = new Intent(this, AddressActivity.class);
        intent.putExtra("consignee", consignee);
        startActivity(intent);
    }

    @Override
    public void onItemDelete(final SPConsigneeAddressBean consignee) {
        AddressStorage.getInstance().deleteData(consignee);
        // mAdapter.notifyDataSetChanged();
        //initData();
        showData();
        addressDeleteHttp(consignee);

    }

    @Override
    public void onItemSetDefault(SPConsigneeAddressBean consignee) {

        if (spConsigneeAddressBeanList != null && spConsigneeAddressBeanList.size() > 0) {
            for (int i = 0; i < spConsigneeAddressBeanList.size(); i++) {
                //Log.i("token222666","请求数据成功======="+spConsigneeAddressBeanList.get(2).getMobile());
                //Log.i("token22277","请求数据成功======="+consignee.getMobile());
                if (consignee.getMobile().equals(spConsigneeAddressBeanList.get(i).getMobile())) {
                    consignee.setIsDefault("1");
                    AddressStorage.getInstance().updataData(consignee);
                    initData();
                    if (getIntent() != null && getIntent().getStringExtra("goods") != null) {
                        Intent intent = new Intent(this, ConfirmOrderActivity.class);
                        intent.putExtra("consignee", consignee);
                        startActivity(intent);
                    }

                } else {
                    SPConsigneeAddressBean consignee2 = spConsigneeAddressBeanList.get(i);
                    consignee2.setIsDefault("0");
                    AddressStorage.getInstance().updataData(consignee2);
                    initData();
                }

            }

        }

    }


    @OnClick({R.id.ib_goods_back, R.id.address_list_pcl, R.id.add_address_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.address_listv:
                break;
            case R.id.address_list_pcl:
                break;
            case R.id.add_address_btn:
                Intent intent = new Intent(this, AddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void showToast(String msg) {
        SPDialogUtils.showToast(this, msg);
    }

    public void hideLoadingToast() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    public void showLoadingToast() {
        showLoadingToast();
    }
}
