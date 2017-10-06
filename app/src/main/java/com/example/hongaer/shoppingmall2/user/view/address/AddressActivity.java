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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.hongaer.shoppingmall2.R;
import com.example.hongaer.shoppingmall2.app.MyApplication;
import com.example.hongaer.shoppingmall2.user.bean.ProvinceBean;
import com.example.hongaer.shoppingmall2.user.bean.SPConsigneeAddressBean;
import com.example.hongaer.shoppingmall2.user.utils.AddressStorage;
import com.example.hongaer.shoppingmall2.utils.CacheUtils;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    @BindView(R.id.consignee_setdefault_sth)
    Switch SetdefaultSth;


   // OptionsPickerView pvOptions;
    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();

    private String url = Constans.ADRRES_URL;
    public boolean isLoaded;
    private String province_data_json;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private OptionsPickerView pvOption;
    private SPConsigneeAddressBean consignee;
    private String province;
    private String city;
    private String area;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
         token= CacheUtils.getString(MyApplication.getContex(), "token");
        //  创建选项选择器
        getHttp();
        initData();
        intSubView();
    }

    private void intSubView() {
           SetdefaultSth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                         if(isChecked){
                            consignee.setIsDefault("1");
                         }else {
                             consignee.setIsDefault("0");
                         }
               }
           });
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
            if ("1".equals(consignee.getIsDefault())){
               SetdefaultSth.setChecked(true);
            }else{
              SetdefaultSth.setChecked(false);
            }

        }
    }


    private boolean checkData() {
        if (TextUtils.isEmpty(etReceived.getText().toString())) {
            showToast("请输入收货人");
            return false;
        }
        consignee.setConsignee(etReceived.getText().toString());
       /////////////
        if (TextUtils.isEmpty(etPhone.getText().toString())) {
            showToast("请输入联系方式");
            return false;
        }
        consignee.setMobile(etPhone.getText().toString());

        ////////////////
        if (TextUtils.isEmpty(etArea.getText().toString())) {
            showToast("请输入所在地区");
            return false;
        }
        consignee.setFullAddress(etArea.getText().toString());
        if(province!=null)
            consignee.setProvince(province);
        if(city!=null)
            consignee.setCity(city);
        if(area!=null)
            consignee.setDistrict(area);
        /////////////////
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

    private void showPickerView() {

        pvOption = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //String city = provinceBeanList.get(options1).getPickerViewText();
                String address;

                address = provinceBeanList.get(options1).getPickerViewText()
                        + " " + cityList.get(options1).get(options2)
                        + " " + districtList.get(options1).get(options2).get(options3);
                 /* }*/
                etArea.setText(address);

                province=provinceBeanList.get(options1).getPickerViewText();
                city=cityList.get(options1).get(options2);
                area=districtList.get(options1).get(options2).get(options3);
                /*Log.i("22277","请求数据成功======="+province);
                Log.i("22277","请求数据成功======="+city);
                Log.i("22277","请求数据成功======="+area);*/
            }

        })
                .setTitleText("请选择省市")
                .setDividerColor(Color.BLACK)
                .setCancelColor(Color.BLACK)
                .setContentTextSize(25)
                .build();
        pvOption.setPicker(provinceBeanList, cityList, districtList);


    }

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
    private void addressAddHttp(SPConsigneeAddressBean consignee){

        String url=Constans.ADDRESS_EDIT_URL;
        Map<String,String> map=new HashMap<>();
        map.put("id","");
        map.put("acceptname",consignee.getConsignee());
        map.put("province","海南");
        map.put("city","海口");
        map.put("area","海南");
        map.put("address","海钓的");
        map.put("zip","0");
        map.put("mobile",consignee.getMobile());
        map.put("is_default",consignee.getIsDefault());
        map.put("token",token);
        Log.i("22277","请求数据成功======="+ map);
        Http.doPost(url, map, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json=response.body().string();
                    Log.i("22277","请求数据成功======="+json);
                }

            }
        });



    }
    private void addressEditHttp(SPConsigneeAddressBean consignee){

        String url=Constans.ADDRESS_EDIT_URL;
        Map<String,String> map=new HashMap<>();
        map.put("id",consignee.getAddressID());
        map.put("acceptname",consignee.getConsignee());
        map.put("province","海南");
        map.put("city","海口");
        map.put("area","海南");
        map.put("address",consignee.getAddress());
        map.put("zip","0");
        map.put("mobile",consignee.getMobile());
        map.put("is_default",consignee.getIsDefault());
        map.put("token",token);
        Log.i("22277","请求数据成功======="+ map);
        Http.doPost(url, map, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json=response.body().string();
                    Log.i("22277","请求数据成功======="+json);
                }

            }
        });



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



    @OnClick({R.id.ib_goods_back, R.id.et_area, R.id.submit_btn, R.id.consignee_setdefault_sth})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.consignee_setdefault_sth:
                break;
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
                if (checkData()) {

                    if (getIntent() != null && getIntent().getSerializableExtra("consignee") != null) {
                        addressEditHttp(consignee);
                        AddressStorage.getInstance().updataData(consignee);
                        Toast.makeText(AddressActivity.this, "修改地址成功", Toast.LENGTH_SHORT).show();

                    } else {
                        AddressStorage.getInstance().addData(consignee);
                        Toast.makeText(AddressActivity.this, "添加地址成功", Toast.LENGTH_SHORT).show();
                       addressAddHttp(consignee);

                    }

                    finish();
                    break;
                }
        }
    }

    public void showToast(String msg) {
        SPDialogUtils.showToast(this, msg);
    }




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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}

