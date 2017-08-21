package com.example.hongaer.shoppingmall2.user.view.address;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.user.bean.ProvinceBean;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.user.utils.AddressStorage;
import com.example.hongaer.shoppingmall2.utils.Constans;
import com.example.hongaer.shoppingmall2.utils.Http;
import com.example.hongaer.shoppingmall2.utils.SPDialogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.example.hongaer.shoppingmall2.home.bean.consignee;

public class AddressActivity extends AppCompatActivity {
    @BindView(R.id.et_received)
    EditText etReceived;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_more_address)
    EditText etMoreAddress;
    @BindView(R.id.ib_goods_back)
    ImageButton ibGoodsBack;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.submit_btn)
    Button submitBtn;


    OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    // private EditText etPhone;
    private String url = Constans.ADRRES_URL;
    public boolean isLoaded;
    private String province_data_json;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private OptionsPickerView pvOption;
   // private consignee consignee;
     private  SPConsigneeAddressBean consignee;

    // private EditText etArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        //  创建选项选择器


        getHttp();
        initData();


    }

    public void initData() {

        if (getIntent() != null && getIntent().getSerializableExtra("consignee") != null) {
            consignee = (SPConsigneeAddressBean) getIntent().getSerializableExtra("consignee");

        }

        if (consignee == null) {
            consignee = new SPConsigneeAddressBean();
            consignee.setIsDefault("0");
        } else {
            etReceived.setText(consignee.getConsignee());
            etPhone.setText(consignee.getMobile());
            etMoreAddress.setText(consignee.getAddress());
            etArea.setText(consignee.getFullAddress());
            /*if ("1".equals(consignee.getIsDefault())){
                consigneeSetdefaultSth.setChecked(true);
            }else{
                consigneeSetdefaultSth.setChecked(false);
            }
            String firstPart = SPPersonDao.getInstance(this).queryFirstRegion(consignee.getProvince() , consignee.getCity() , consignee.getDistrict() , consignee.getTown());
            regionTxtv.setText(firstPart);
        }*/
        }
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(etReceived.getText().toString())) {
            showToast("请输入收货人");
            return false;
        }
        consignee.setConsignee(etReceived.getText().toString());
       //
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            showToast("请输入联系方式");
            return false;
        }
        consignee.setMobile(etPhone.getText().toString());

        //
        if (TextUtils.isEmpty(etArea.getText().toString())) {
            showToast("请输入所在地区");
            return false;
        }
        consignee.setFullAddress(etArea.getText().toString());

        //
        if (TextUtils.isEmpty(etMoreAddress.getText().toString())) {
            showToast("请输入详细地址");
            return false;
        }
        consignee.setAddress(etMoreAddress.getText().toString());

        return true;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_LOAD_SUCCESS:
                    // Toast.makeText(AddressActivity.this, "解析数据成功", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    //  Toast.makeText(AddressActivity.this,"解析数据失败",Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };
    //etPhone= (EditText) findViewById(R.id.et_phone);

    // etArea = (EditText) findViewById(R.id.et_area);

     /*   mTextViewAddress = (TextView) findViewById(R.id.tv_address);*/

    //  获取json数据
       /* String province_data_json = JsonFileReader.getJson(this, "province_data.json");*/
    //  解析json数据

    //parseJson(province_data_json);
    //设置三级联动效果
    //设置选择的三级单位
    // pvOptions.setLabels("省", "市", "区");
    // pvOptions.setTitle("选择城市");


    private void showPickerView() {
        //pvOptions = new OptionsPickerView(this);
        // if (cityList != null && cityList.size() > 0 && districtList != null && districtList.size() > 0 && provinceBeanList != null && provinceBeanList.size() > 0) {
        //pvOptions.setPicker(provinceBeanList, cityList, districtList, true);
        //  设置是否循环滚动

        //pvOptions.setCyclic(false, false, false);
        // 设置默认选中的三级项目
        // pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
          /*  pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    if (provinceBeanList != null && provinceBeanList.size() > 0) {
                    String city = provinceBeanList.get(options1).getPickerViewText();
                    String address;
                    //  如果是直辖市或者特别行政区只设置市和区/县
                    // if (cityList != null && cityList.size() > 0 && districtList != null && districtList.size() > 0&&provinceBeanList!=null&&provinceBeanList.size()>0) {

                    if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city)|| "澳门特别行政区".equals(city)|| "台湾省".equals(city)|| "香港特别行政区".equals(city)) {
                        address = provinceBeanList.get(options1).getPickerViewText()
                                + " " + districtList.get(options1).get(option2).get(options3);
                    } else {
                        address = provinceBeanList.get(options1).getPickerViewText()
                                + " " + cityList.get(options1).get(option2)
                                + " " + districtList.get(options1).get(option2).get(options3);
                    }
                    etArea.setText(address);
                }
                  }

                // }
                // }
            });*/
        pvOption = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String city = provinceBeanList.get(options1).getPickerViewText();
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                // if (cityList != null && cityList.size() > 0 && districtList != null && districtList.size() > 0&&provinceBeanList!=null&&provinceBeanList.size()>0) {

                 /* if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city)*//*|| "澳门特别行政区".equals(city)|| "台湾省".equals(city)|| "香港特别行政区".equals(city)*//*) {
                      address = provinceBeanList.get(options1).getPickerViewText()
                              + " " + districtList.get(options1).get(options2).get(options3);
                  } else if("澳门特别行政区".equals(city)|| "台湾省".equals(city)|| "香港特别行政区".equals(city)){
                      address = provinceBeanList.get(options1).getPickerViewText()
                              + " " + cityList.get(options1).get(options2);

                  } else {*/
                address = provinceBeanList.get(options1).getPickerViewText()
                        + " " + cityList.get(options1).get(options2)
                        + " " + districtList.get(options1).get(options2).get(options3);
                 /* }*/
                etArea.setText(address);
            }


        })
                .setTitleText("请选择省市")
                .setDividerColor(Color.BLACK)
                .setCancelColor(Color.BLACK)
                .setContentTextSize(25)
                .build();
        pvOption.setPicker(provinceBeanList, cityList, districtList);
    }

    // }

     /*   //  点击弹出选项选择器
        mTextViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvOptions.show()
            }
        });*/


    private void getHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String province_data_json = Http.get(url);
                    Log.i("tag888999", "请求数据成功=======" + province_data_json);
                    //parseJson(province_data_json);
                    getJson(province_data_json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void getJson(String data) {
        Gson gson = new Gson();
        HashMap map = gson.fromJson(data, HashMap.class);

        Set<String> keyset = map.keySet();

        for (String key : keyset) {

            provinceBeanList.add(new ProvinceBean(key));
            Log.i("key值为=====" + key, "value值为=====" + map.get(key));
            try {

                Map value = (Map) map.get(key);
                if (value != null && value.size() > 0) {
                    cities = new ArrayList<>();//   声明存放城市的集合
                    districts = new ArrayList<>(); //声明存放区县集合的集合
                    Set<String> keyset2 = value.keySet();
                    for (String key2 : keyset2) {
                        Log.i("key2值为=====" + key2, "value2值为=====" + value.get(key2));

                        cities.add(key2);
                        district = new ArrayList<>();
                        ArrayList<String> array = (ArrayList<String>) value.get(key2);
                        if (array.size() > 0 && array != null) {
                            for (int k = 0; k < array.size(); k++) {
                                String areaName = array.get(k);
                                Log.i("key3" + k, "value3=====" + areaName);
                                district.add(areaName);
                            }
                        } else {
                            district.add("");
                        }
                        districts.add(district);
                    }
                }
                districtList.add(districts);
                cityList.add(cities);


            } catch (Exception e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
            }
            mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
        }


    }

    @OnClick({R.id.ib_goods_back, R.id.et_area,R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_back:
                finish();
                break;
            case R.id.et_area:
                if (isLoaded) {
                    showPickerView();
                    pvOption.show();
                } else {
                    Toast.makeText(AddressActivity.this, "数据暂未解析成功，请等待", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.submit_btn:
                   if(!checkData()){

                       return;
                   }else if(getIntent() != null && getIntent().getSerializableExtra("consignee") != null){
                           AddressStorage.getInstance().updataData(consignee);
                   }else {
                       AddressStorage.getInstance().addData(consignee);
                   }


                Log.i("consignee","======"+consignee);
               /* CacheUtils.saveString(MyApplication.getContex(),"address",consignee.getAddress());
                CacheUtils.saveString(MyApplication.getContex(),"mobile",consignee.getMobile());
                CacheUtils.saveString(MyApplication.getContex(),"is_default",consignee.getIsDefault());
                CacheUtils.saveString(MyApplication.getContex(),"etArea",etArea.getText().toString());*/
                /*if (!TextUtils.isEmpty(consignee.getAddressID())) {//编辑
                    CacheUtils.saveString(MyApplication.getContex(),"address_id",consignee.getAddressID());

                }*/
                  finish();
                break;
        }
    }

    public void showToast(String msg) {
        SPDialogUtils.showToast(this, msg);
    }

    // }


    //}



/*

          cities = new ArrayList<>();//   声明存放城市的集合
          districts = new ArrayList<>();
*/


          /*Iterator<String> it = keyset.iterator();
          while(it.hasNext()){
              String key = it.next();
              String value= (String) map.get(key);
              Log.i("key值为====="+key,"value值为====="+value);

          }
*/


    //  解析json填充集合
  /*  public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


}

