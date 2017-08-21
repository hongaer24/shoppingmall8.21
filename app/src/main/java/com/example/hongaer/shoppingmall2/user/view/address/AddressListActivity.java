package com.example.hongaer.shoppingmall2.user.view.address;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.user.adapter.SPAddressListAdapter;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.user.utils.AddressStorage;
import com.example.hongaer.shoppingmall2.utils.SPDialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressListActivity extends AppCompatActivity implements  SPAddressListAdapter.AddressListListener{

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
    private  List<SPConsigneeAddressBean> spConsigneeAddressBeanList;
    SPAddressListAdapter mAdapter;
    //SPConsigneeAddressBean selectConsignee;
    private DialogFragment mLoadingDialog;

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
        spConsigneeAddressBeanList= AddressStorage.getInstance().getAllData();
        mAdapter = new SPAddressListAdapter(this, this,spConsigneeAddressBeanList);
       addressListv.setAdapter(mAdapter);
       // ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {


           /* public void onRefreshBegin(PtrFrameLayout frame) {
                //下拉刷新
                refreshData();
            }
        });
        refreshData();*/
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
        intent.putExtra("consignee" , consignee);
        startActivity(intent);
    }
    @Override
    public void onItemDelete(final SPConsigneeAddressBean consignee) {
       /* if (goodsBeanList != null && goodsBeanList.size() > 0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                  goodsBean=goodsBeanList.get(i);
                //内存中删除
                  goodsBeanList.remove(goodsBean);*/
                //在本地中保存
         // spConsigneeAddressBeanList= AddressStorage.getInstance().getAllData();
                                    //AddressStorage.getInstance().updataData(consignee);

                //刷新
             /*  addressListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                spConsigneeAddressBeanList.remove(position);
                                  mAdapter.notifyDataSetChanged();
                   }
               });*/
       // AddressStorage.getInstance().deleteData(consignee);

            }




    @Override
    public void onItemSetDefault(SPConsigneeAddressBean consignee) {

    }
   /* public void onItemDelete(SPConsigneeAddressBean consigneeAddress) {
        selectConsignee = consigneeAddress;
        showConfirmDialog("确定删除该地址吗" , "删除提醒" , this , 1 );
    }*/

    @OnClick({R.id.ib_goods_back,R.id.address_list_pcl, R.id.add_address_btn})
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
                  Intent intent=new Intent(this,AddressActivity.class);
                  startActivity(intent);
                break;
        }
    }
    public void showToast(String msg){
        SPDialogUtils.showToast(this, msg);
    }
    public void hideLoadingToast(){
        if(mLoadingDialog !=null){
            mLoadingDialog.dismiss();
        }
    }
    public void showLoadingToast(){
        showLoadingToast( );
    }
}
