package com.example.hongaer.shoppingmall2.user.view.address;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.home.bean.GoodsBean;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.utils.SPDialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressEditActivity extends AppCompatActivity {


    private String TAG = "SPConsigneeAddressEditActivity";
    private GoodsBean goodsBean;
    private SPConsigneeAddressBean selectRegionConsignee;


    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.consignee_name_txtv)
    TextView consigneeNameTxtv;
    @BindView(R.id.consignee_name_edtv)
    EditText consigneeNameEdtv;
    @BindView(R.id.consignee_mobile_txtv)
    TextView consigneeMobileTxtv;
    @BindView(R.id.consignee_mobile_edtv)
    EditText consigneeMobileEdtv;
    @BindView(R.id.consignee_region_title_txtv)
    TextView consigneeRegionTitleTxtv;
    @BindView(R.id.consignee_arrow_imgv)
    ImageView consigneeArrowImgv;
    @BindView(R.id.consignee_region_txtv)
    TextView consigneeRegionTxtv;
    @BindView(R.id.consignee_address_txtv)
    TextView consigneeAddressTxtv;
    @BindView(R.id.consignee_address_edtv)
    EditText consigneeAddressEdtv;
    @BindView(R.id.consignee_setdefault_txtv)
    TextView consigneeSetdefaultTxtv;
    @BindView(R.id.consignee_setdefault_sth)
    Switch consigneeSetdefaultSth;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);
    }


    public void initSubViews() {
        consigneeSetdefaultSth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    goodsBean.setIsDefault("1");
                } else {
                    goodsBean.setIsDefault("0");
                }
            }

        });
    }
        public void initData() {

            if(getIntent()!=null && getIntent().getSerializableExtra("goodsBean") != null){
                goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");
            }

            if (goodsBean == null){
                goodsBean = new GoodsBean();
                goodsBean.setIsDefault("0");
            }else{
                consigneeNameEdtv.setText(goodsBean.getConsignee());
                consigneeMobileTxtv.setText(goodsBean.getMobile());
                consigneeAddressEdtv.setText(goodsBean.getAddress());

                if ("1".equals(goodsBean.getIsDefault())){
                    consigneeSetdefaultSth.setChecked(true);
                }else{
                    consigneeSetdefaultSth.setChecked(false);
                }
              /*  String firstPart = SPPersonDao.getInstance(this).queryFirstRegion(consignee.getProvince() , consignee.getCity() , consignee.getDistrict() , consignee.getTown());
                regionTxtv.setText(firstPart);*/
            }
        }
    private boolean checkData(){
        if (TextUtils.isEmpty(consigneeNameEdtv.getText().toString())){
            showToast("请输入收货人");
            return false;
        }
        goodsBean.setConsignee( consigneeMobileTxtv.getText().toString());

        if (TextUtils.isEmpty( consigneeMobileTxtv.getText().toString())){
            showToast("请输入联系方式");
            return false;
        }
        goodsBean.setMobile(  consigneeMobileTxtv.getText().toString());


        if (TextUtils.isEmpty( consigneeAddressEdtv.getText().toString())){
            showToast("请输入详细地址");
            return false;
        }
        goodsBean.setAddress( consigneeAddressEdtv.getText().toString());


        return true;

     /*   if (TextUtils.isEmpty( consigneeAddressEdtv.getText().toString())){
            showToast("请输入详细地区");
            return false;
        }
        goodsBean.setFullAddress( consigneeAddressEdtv.getText().toString());


        return true;*/
    }

       /* consigneeSetdefaultSth.setOnChangeListener(new OnChangeListener() {
            @Override
            public void onChange(SwitchButton sb, boolean state) {
                if (state) {
                    consignee.setIsDefault("1");
                } else {
                    consignee.setIsDefault("0");
                }
            }
        });*/


    @OnClick({R.id.ib_goods_back, R.id.consignee_name_txtv, R.id.consignee_name_edtv, R.id.consignee_mobile_txtv, R.id.consignee_mobile_edtv, R.id.consignee_region_title_txtv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                 finish();
                break;
            case R.id.consignee_name_txtv:
                break;
            case R.id.consignee_name_edtv:
                break;
            case R.id.consignee_mobile_txtv:
                break;
            case R.id.consignee_mobile_edtv:
                break;
            case R.id.consignee_region_title_txtv:
                break;
        }
    }

    public void showToast(String msg){
        SPDialogUtils.showToast(this, msg);
    }
}
